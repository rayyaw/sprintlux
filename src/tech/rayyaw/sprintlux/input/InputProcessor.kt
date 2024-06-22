package tech.rayyaw.sprintlux.input

import kotlinx.coroutines.flow.MutableStateFlow

public interface InputProcessor {
    fun startPolling()
    fun stopPolling()

    //val globalHotkeysEnabled: MutableStateFlow<Boolean>
    //val splitHotkeyState: MutableStateFlow<InputState>
}