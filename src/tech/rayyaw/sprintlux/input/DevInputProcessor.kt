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

public class DevInputProcessor @Inject constructor(
    private val logger: Logger,
    private val coroutineScope: CoroutineScope,
): InputProcessor {
    companion object {
        const val KEY_PRESSED_VALUE = 1
    }

    private val inputDelegates: MutableList<InputDelegate> = mutableListOf()

    private lateinit var inputStream: FileInputStream
    private lateinit var inputChannel: FileChannel
    
    private var shouldStopPolling: Boolean = false

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

    private suspend fun startPollingSuspend() {
        logger.debug("Start polling for inputs.")

        //val buffer = ByteBuffer.allocate(Config.INPUT_EVENT_STRUCT_SIZE)
        val byteArray = ByteArray(Config.INPUT_EVENT_STRUCT_SIZE)

        while (!shouldStopPolling) {
            // Read bytes into the buffer
            val bytesRead = inputStream.read(byteArray)

            // Parse into an InputState event
            val inputEvent = DevInputEvent.fromByteArray(byteArray)

            // Filter out non-keypress events
            if (inputEvent.type != Config.EV_KEY_C_STRUCT_VALUE) continue

            // Filter out repeated keypresses, and releases
            if (inputEvent.value != KEY_PRESSED_VALUE) continue

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