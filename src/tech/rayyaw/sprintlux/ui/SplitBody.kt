package tech.rayyaw.sprintlux.ui

import com.google.inject.name.Named
import java.time.Duration
import java.time.Instant
import javax.inject.Inject
import javafx.stage.Stage
import javafx.scene.control.Label
import javafx.scene.layout.Priority
import javafx.scene.layout.Region
import javafx.scene.layout.VBox
import javafx.scene.layout.HBox
import javafx.geometry.Pos
import kotlinx.coroutines.flow.MutableStateFlow
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
    @Named("startTime") private val startTime: MutableStateFlow<Instant?>
): VBox() {

    private var splits: SplitFile?
    private val guiSplits: List<HBox>?

    init {
        splits = splitProvider.splitFile.value

        // FIXME - update when new deltas are available
        // and when split file changes
        guiSplits = splits?.splits?.map {
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

        guiSplits?.let {
            children.addAll(guiSplits)
        }
    }

    fun notifySplit(split: Int, time: Instant) {

        val timeSinceStart = Duration.between(startTime.value, time)
        .toMillis()
        val pbTimeSinceStart = splits?.splits?.get(split)?.pbTimeMillis
        
        pbTimeSinceStart?.let {
            val pbDelta = timeSinceStart - pbTimeSinceStart
            splitProvider.splitFile.value?.setDelta(split, pbDelta)
            logger.debug("Your current delta: $pbDelta")
        }

        splits = splitProvider.splitFile.value

        // FIXME - update PB delta and displayed split time
    }
}