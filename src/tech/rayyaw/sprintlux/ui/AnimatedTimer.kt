package tech.rayyaw.sprintlux.ui

import java.time.Duration
import java.time.Instant
import javafx.animation.AnimationTimer
import javafx.scene.control.Label
import kotlinx.coroutines.flow.MutableStateFlow
import tech.rayyaw.sprintlux.util.DurationFormatter

// An animated timer to keep your splits up-to-date.
// FIXME - support stopping the timer
class AnimatedTimer(
    // The timer will update according to this value.
    // If the underlying value is null, the timer will freeze at 0.
    private val startTime: MutableStateFlow<Instant?> 
): Label() {
    
    private var duration: Duration = Duration.ofMillis(0L)

    init {
        val animationTimer = object : AnimationTimer() {
            override fun handle(now: Long) {
                duration = startTime.value?.let {
                    Duration.between(it, Instant.now()) 
                } ?: Duration.ofMillis(0L)
                text = DurationFormatter.formatDuration(duration)
            }
        }
        animationTimer.start()
    }
}