package tech.rayyaw.sprintlux.input

import kotlinx.coroutines.flow.MutableStateFlow

public interface InputProcessor {
    fun startPolling()
    fun stopPolling()

    fun registerDelegate(delegate: InputDelegate)
    fun deregisterDelegate(delegate: InputDelegate)
}