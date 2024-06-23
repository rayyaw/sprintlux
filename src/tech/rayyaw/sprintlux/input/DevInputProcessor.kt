package tech.rayyaw.sprintlux.input

import java.io.File
import java.io.FileInputStream
import java.nio.channels.FileChannel
import java.nio.ByteBuffer
import java.awt.event.KeyEvent
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import org.slf4j.Logger
import tech.rayyaw.sprintlux.config.Config
import tech.rayyaw.sprintlux.config.LinuxKeyMappings

/**
 * Process input from /dev/input.
 * This is not the greatest solution, as we need to interact with input streams designed for C
 * but it allows us to have global hotkeys.
 * 
 * Another caveat of this solution is it requires the user to be in the `input` group to use.
 */
public class DevInputProcessor @Inject constructor(
    private val logger: Logger,
    private val coroutineScope: CoroutineScope,
): InputProcessor {
    companion object {
        // This is a standard value and part of the /dev/input API,
        // so no need to have it as a config value
        const val KEY_PRESSED_VALUE = 1
    }

    // Input delegates are listening for input, and will receive a callback on split events
    private val inputDelegates: MutableList<InputDelegate> = mutableListOf()

    private lateinit var inputStream: FileInputStream
    private lateinit var inputChannel: FileChannel

    private var shouldStopPolling: Boolean = false

    // Launch the input poller on a separate thread.
    override fun startPolling() {
        val file = File(Config.INPUT_FILE)
        inputStream = FileInputStream(file)
        inputChannel = inputStream.channel

        coroutineScope.launch {
            startPollingSuspend()
        }
    }

    override fun stopPolling() {
        shouldStopPolling = true
        inputChannel.close()
        inputStream.close()
    }

    // Run listener on /dev/input/eventX without blocking the main thread
    private suspend fun startPollingSuspend() {
        logger.debug("Start polling for inputs.")
        val byteArray = ByteArray(Config.INPUT_EVENT_STRUCT_SIZE)

        while (!shouldStopPolling) {
            val bytesRead = inputStream.read(byteArray)
            val inputEvent = DevInputEvent.fromByteArray(byteArray)

            // Filter out non-keypress events
            if (inputEvent.type != Config.EV_KEY_C_STRUCT_VALUE) continue

            // Filter out repeated keypresses, and releases
            if (inputEvent.value != KEY_PRESSED_VALUE) continue

            // Convert to key code being pressed
            val keypressType: InputState? = when (inputEvent.code.toInt()) {
                LinuxKeyMappings.mappings[Config.GLOBAL_HOTKEY_BUTTON] -> InputState.GLOBAL_HOTKEY_TOGGLE
                LinuxKeyMappings.mappings[Config.SPLIT_BUTTON] -> InputState.SPLIT
                LinuxKeyMappings.mappings[Config.SKIP_SPLIT_BUTTON] -> InputState.SKIP_SPLIT
                LinuxKeyMappings.mappings[Config.UNDO_SPLIT_BUTTON] -> InputState.UNDO_SPLIT
                LinuxKeyMappings.mappings[Config.RESET_BUTTON] -> InputState.RESET

                else -> null
            }

            logger.debug("Read $bytesRead raw bytes from /dev/input: $byteArray")
            logger.debug("The ${keypressType} key was pressed")

            // Notify delegates
            keypressType?.let { keypress ->
                inputDelegates.map { delegate ->
                    delegate.onInputChanged(keypress)
                }
            }
        }
    }

    override fun registerDelegate(delegate: InputDelegate) {
        inputDelegates.add(delegate)
    }

    override fun deregisterDelegate(delegate: InputDelegate) {
        inputDelegates.remove(delegate)
    }
    
}