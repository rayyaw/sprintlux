package tech.rayyaw.sprintlux.input

import java.io.File
import java.io.FileInputStream
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.flow.MutableStateFlow
import org.slf4j.Logger
import tech.rayyaw.sprintlux.config.Config

public class DevInputProcessor @Inject constructor(
    private val logger: Logger,
    private val coroutineScope: CoroutineScope,
): InputProcessor {

    private lateinit var inputStream: FileInputStream

    override fun startPolling() {
        val file = File(Config.INPUT_FILE)
        inputStream = FileInputStream(file)

        runBlocking { 
            startPollingSuspend()
        }
    }

    override fun stopPolling() {
        inputStream.close()
    }

    private suspend fun startPollingSuspend() {
        // TODO - handle input polling
        // (ideally, set up callbacks for extra data)
        logger.debug("Start polling for inputs.")
    }

    val globalHotkeysEnabled: MutableStateFlow<Boolean> = MutableStateFlow(true)

    val splitHotkeyState: MutableStateFlow<InputState> = MutableStateFlow(InputState.EMPTY)
    
}