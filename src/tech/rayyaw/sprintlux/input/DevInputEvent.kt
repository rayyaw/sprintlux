package tech.rayyaw.sprintlux.input

import java.nio.ByteBuffer
import java.nio.ByteOrder
import tech.rayyaw.sprintlux.config.Config

// defined at /usr/include/linux/input.h -> struct input_event
data class DevInputEvent (
    val inputEventSec: Long,   // Corresponds to __kernel_ulong_t __sec
    val inputEventUsec: Long,  // Corresponds to __kernel_ulong_t __usec
    val type: UShort,
    val code: UShort,
    val value: Int,
) {
    companion object {
        fun fromByteArray(bytes: ByteArray): DevInputEvent {
            val buffer = ByteBuffer.wrap(bytes)

            buffer.order(Config.SYSTEM_ENDIANNESS)

            val inputEventSec = buffer.getLong()
            val inputEventUsec = buffer.getLong()
            val type = buffer.getShort().toUShort()
            val code = buffer.getShort().toUShort()
            val value = buffer.getInt()

            return DevInputEvent(inputEventSec, inputEventUsec, type, code, value)
        }
    }
}