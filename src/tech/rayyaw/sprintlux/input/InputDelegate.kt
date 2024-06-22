package tech.rayyaw.sprintlux.input

/**
 * Used to request callbacks for input events.
 * Your function will be called when a new input is detected, assuming it is registered correctly.
 */
interface InputDelegate {
    fun onInputChanged(newInput: InputState)
}