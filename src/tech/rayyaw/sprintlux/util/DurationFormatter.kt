package tech.rayyaw.sprintlux.util

import java.time.Duration
import tech.rayyaw.sprintlux.config.Config

object DurationFormatter {
    fun formatDuration(duration: Duration): String {
        // FIXME - support showing only seconds, tenths, or hundredths
        val hours = duration.abs().toHours();
        val minutes = duration.abs().toMinutes() % 60
        val seconds = duration.abs().getSeconds() % 60
        val millis = duration.abs().toMillis() % 1000

        val durationSign = if (duration.isNegative()) "-" else ""

        return durationSign + if (hours != 0L) {
            String.format(
                "%d:%02d:%02d.%03d", hours, minutes, seconds, millis
            )
        } else if (minutes != 0L) {
            String.format(
                "%d:%02d.%03d", minutes, seconds, millis
            )
        } else {
            String.format(
                "%d.%03d", seconds, millis
            )
        }
    }
}