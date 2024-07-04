package tech.rayyaw.sprintlux.input

interface InputDelegate {
    fun onInputChanged(newInput: InputState?)
}