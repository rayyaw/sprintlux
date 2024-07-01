package tech.rayyaw.sprintlux.input

import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Module for processing inputs and sending them to frontend delegates.
 */
public interface InputProcessor {
    // Put any initial setup/destruction your class here
    fun startPolling()
    fun stopPolling()

    val event: MutableStateFlow<InputState>
}