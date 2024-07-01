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
import tech.rayyaw.sprintlux.split.SplitFile
import tech.rayyaw.sprintlux.split.SplitProvider
import tech.rayyaw.sprintlux.util.DurationFormatter

// Displays the split body
// which contains split names, time deltas, and comparison times.
class SplitBody @Inject constructor(
    private val splitProvider: SplitProvider,
    private val logger: Logger,
): VBox() {

    private val splits: SplitFile
    private val guiSplits: List<HBox>

    init {
        splits = splitProvider.splitFile.value

        // FIXME - update when new deltas are available
        guiSplits = splits.splits.map {
            val splitDisplayer = HBox()
            val splitName = Label(it.splitName)

            val spacer = Region()
            HBox.setHgrow(spacer, Priority.ALWAYS)

            // FIXME - the delta needs to be colored correctly
            val pbDelta = it.pbDeltaMillis?.let { Label(
                DurationFormatter.formatDuration(
                    Duration.ofMillis(it)
                )
            )}

            val pbTime = Label(
                DurationFormatter.formatDuration(
                    Duration.ofMillis(it.pbTimeMillis)
                )
            )

            val itemsToDisplay = listOf(
                splitName, spacer, pbDelta, pbTime
            ).filterNotNull()

            splitDisplayer.children.addAll(itemsToDisplay)
            splitDisplayer.setSpacing(Config.SPLIT_HORIZONTAL_SPACING)
            splitDisplayer
        }

        children.addAll(guiSplits)
    }
}