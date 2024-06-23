package tech.rayyaw.sprintlux.config

import java.nio.ByteOrder

// TODO: Migrate a bunch of this stuff to layout files
object Config {
    // Global hotkeys
    const val GLOBAL_HOTKEY_BUTTON:   String = "Grave"

    const val SPLIT_BUTTON:           String = "Z"
    const val SKIP_SPLIT_BUTTON:      String = "X"
    const val UNDO_SPLIT_BUTTON:      String = "A"
    const val RESET_BUTTON:           String = "S"

    // Input polling
    const val INPUT_FILE: String = "/dev/input/event3"
    const val INPUT_EVENT_STRUCT_SIZE: Int = 24
    val EV_KEY_C_STRUCT_VALUE: UShort = 1.toUShort() // C enum value corresponding to a key press
    val SYSTEM_ENDIANNESS: ByteOrder = ByteOrder.LITTLE_ENDIAN

    // Layout settings
    const val LAUNCH_WIDTH: Double                  = 200.0
    const val SPLIT_HORIZONTAL_SPACING: Double      = 10.0
    const val BACKGROUND_COLOR: String              = "(100, 100, 100)"
    const val FONT_COLOR: String                    = "(255, 255, 255)"

    // User defined files
    const val SPLIT_FILE: String = "res/example.json"
    const val STYLESHEET: String = "res/style.css"
}

// You shouldn't need to modify this
// unless you notice key presses are registering with incorrect codes
// or keys aren't mapped
object LinuxKeyMappings {
    val mappings = mapOf(
        "Esc" to 1,
        "N1" to 2,
        "N2" to 3,
        "N3" to 4,
        "N4" to 5,
        "N5" to 6,
        "N6" to 7,
        "N7" to 8,
        "N8" to 9,
        "N9" to 10,
        "N0" to 11,
        "Minus" to 12,
        "Equal" to 13,
        "Backspace" to 14,
        "Tab" to 15,
        "Q" to 16,
        "W" to 17,
        "E" to 18,
        "R" to 19,
        "T" to 20,
        "Y" to 21,
        "U" to 22,
        "I" to 23,
        "O" to 24,
        "P" to 25,
        "Leftbrace" to 26,
        "Rightbrace" to 27,
        "Enter" to 28,
        "Leftctrl" to 29,
        "A" to 30,
        "S" to 31,
        "D" to 32,
        "F" to 33,
        "G" to 34,
        "H" to 35,
        "J" to 36,
        "K" to 37,
        "L" to 38,
        "Semicolon" to 39,
        "Apostrophe" to 40,
        "Grave" to 41,
        "Leftshift" to 42,
        "Backslash" to 43,
        "Z" to 44,
        "X" to 45,
        "C" to 46,
        "V" to 47,
        "B" to 48,
        "N" to 49,
        "M" to 50,
        "Comma" to 51,
        "Dot" to 52,
        "Slash" to 53,
        "Rightshift" to 54,
        "Kpasterisk" to 55,
        "Leftalt" to 56,
        "Space" to 57,
        "Capslock" to 58,
        "F1" to 59,
        "F2" to 60,
        "F3" to 61,
        "F4" to 62,
        "F5" to 63,
        "F6" to 64,
        "F7" to 65,
        "F8" to 66,
        "F9" to 67,
        "F10" to 68,
        "Numlock" to 69,
        "Scrolllock" to 70,
        "F11" to 87,
        "F12" to 88,
        "Rightctrl" to 97,
        "Rightalt" to 100,
        "Linefeed" to 101,
        "Home" to 102,
        "Up" to 103,
        "PageUp" to 104,
        "Left" to 105,
        "Right" to 106,
        "End" to 107,
        "Down" to 108,
        "Pagedown" to 109,
        "Insert" to 110,
        "Delete" to 111,
        "Pause" to 119,
        "Leftmeta" to 125,
        "Rightmeta" to 126,
    )
}