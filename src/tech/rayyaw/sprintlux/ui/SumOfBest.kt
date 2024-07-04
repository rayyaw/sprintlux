package tech.rayyaw.sprintlux.ui

import java.time.Duration
import javax.inject.Inject
import javafx.application.Platform
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

    private val sobTimeLabel: Label = Label()

    init {
        val sobText = Label("Sum of Best Segments:")

        val spacer = Region()
        HBox.setHgrow(spacer, Priority.ALWAYS)

        updateSobLabel()

        children.addAll(sobText, spacer, sobTimeLabel)
    }

    fun updateSobLabel() {
        val splits = splitProvider.splitFile.value
        splits?.let {
            val sobTime = splits.splits.map {
                it.goldTimeMillis
            }.sum()

            val sobTimeText = DurationFormatter.formatDuration(
                Duration.ofMillis(sobTime)
            )

            Platform.runLater {
                sobTimeLabel.text = sobTimeText
            }
        }       
    }
}