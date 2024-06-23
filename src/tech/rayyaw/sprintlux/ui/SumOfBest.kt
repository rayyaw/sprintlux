package tech.rayyaw.sprintlux.ui

import java.time.Duration
import javax.inject.Inject
import javafx.stage.Stage
import javafx.scene.control.Label
import javafx.scene.layout.Priority
import javafx.scene.layout.Region
import javafx.scene.layout.VBox
import javafx.scene.layout.HBox
import javafx.geometry.Pos
import org.slf4j.Logger
import tech.rayyaw.sprintlux.config.Config
import tech.rayyaw.sprintlux.split.SplitProvider
import tech.rayyaw.sprintlux.util.DurationFormatter

// Displays the sum of best.
class SumOfBest @Inject constructor(
    private val splitProvider: SplitProvider,
    private val logger: Logger,
): HBox() {
    init {
        val sobText = Label("Sum of Best Segments:")

        val spacer = Region()
        HBox.setHgrow(spacer, Priority.ALWAYS)

        val sobTime = splitProvider.splitFile.value.splits.map {
            it.goldTimeMillis
        }.sum()

        val sobTimeLabel = Label(
            DurationFormatter.formatDuration(
                Duration.ofMillis(sobTime)
            )
        )

        children.addAll(sobText, spacer, sobTimeLabel)
    }
}