package tech.rayyaw.sprintlux.ui

import java.time.Duration
import javax.inject.Inject
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import tech.rayyaw.sprintlux.util.DurationFormatter

// Displays the running time.
class BigTimer @Inject constructor(

): VBox() {
    var duration: Duration = Duration.ofMillis(0L)

    init {
        // FIXME - Add color as needed
        // FIXME - make the font larger
        val timeLabel = Label(DurationFormatter.formatDuration(duration))

        alignment = Pos.CENTER_RIGHT
        children.add(timeLabel)
    }
}