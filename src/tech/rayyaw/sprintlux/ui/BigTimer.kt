package tech.rayyaw.sprintlux.ui

import com.google.inject.name.Named
import java.time.Duration
import java.time.Instant
import javax.inject.Inject
import javafx.animation.AnimationTimer
import javafx.application.Platform
import javafx.geometry.Pos
import javafx.scene.layout.VBox
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import org.slf4j.Logger
import tech.rayyaw.sprintlux.ui.AnimatedTimer
import tech.rayyaw.sprintlux.util.DurationFormatter

// Displays the running time.
class BigTimer @Inject constructor(
    private val logger: Logger,
    private val coroutineScope: CoroutineScope,
    @Named("startTime") private val startTime: MutableStateFlow<Instant?>
): VBox() {
    private val timeLabel: AnimatedTimer

    init {
        // FIXME - Add color as needed
        // FIXME - make the font larger
        timeLabel = AnimatedTimer(startTime)

        alignment = Pos.CENTER_RIGHT
        children.add(timeLabel)
    }
}