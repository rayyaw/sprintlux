package tech.rayyaw.sprintlux.input

/**
 * Module for processing inputs and sending them to frontend delegates.
 */
public interface InputProcessor {
    // Put any initial setup/destruction your class here
    fun startPolling()
    fun stopPolling()

    // FIXME - use MutableStateFlow instead of delegates

    // Register and deregister an input delegate.
    // All input delegates should receive a callback when a relevant keypress happens.
    fun registerDelegate(delegate: InputDelegate)
    fun deregisterDelegate(delegate: InputDelegate)
}