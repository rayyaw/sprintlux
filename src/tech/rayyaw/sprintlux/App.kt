package tech.rayyaw.sprintlux

import com.google.inject.Guice
import com.google.inject.name.Named
import java.time.Duration
import java.time.Instant
import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.stage.Stage
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.MutableStateFlow
import org.slf4j.Logger
import tech.rayyaw.sprintlux.config.Config
import tech.rayyaw.sprintlux.input.InputDelegate
import tech.rayyaw.sprintlux.input.InputProcessor
import tech.rayyaw.sprintlux.input.InputState
import tech.rayyaw.sprintlux.split.SplitFile
import tech.rayyaw.sprintlux.split.SplitProvider
import tech.rayyaw.sprintlux.ui.SplitHeader
import tech.rayyaw.sprintlux.ui.AttemptCounter
import tech.rayyaw.sprintlux.ui.SplitBody
import tech.rayyaw.sprintlux.ui.BigTimer
import tech.rayyaw.sprintlux.ui.SumOfBest

// TODO: Dynamic updating (ie, don't show a static screen), edit config via right click menu
class Main: Application(), InputDelegate {
    // Processing engine
    @Inject lateinit var logger: Logger
    @Inject lateinit var inputProcessor: InputProcessor
    @Inject lateinit var coroutineScope: CoroutineScope
    @Inject lateinit var splitProvider: SplitProvider

    // Views
    @Inject lateinit var splitHeader: SplitHeader
    @Inject lateinit var attemptCounter: AttemptCounter
    @Inject lateinit var splitBody: SplitBody
    @Inject lateinit var bigTimer: BigTimer
    @Inject lateinit var sumOfBest: SumOfBest

    // Dynamic datasplitPr
    @Inject @Named("startTime") lateinit var startTime: MutableStateFlow<Instant?>
    var lastSplitTime:      Instant? = null
    var currentSplit:       Int = -1
    var splitData:          MutableStateFlow<SplitFile?>

    init {
        val injector = Guice.createInjector(AppModule())
        injector.injectMembers(this)

        splitData = splitProvider.splitFile
    }

    // UI methods
    override fun start(primaryStage: Stage) {
        inputProcessor.startPolling()
        inputProcessor.registerDelegate(this)

        // FIXME - set global font, font size and color (should be configurable)

        // FIXME - A horizontal line would look nicer, but is harder to execute
        VBox.setMargin(attemptCounter, Insets(0.0, 0.0, 10.0, 0.0))
        VBox.setMargin(splitBody, Insets(0.0, 0.0, 10.0, 0.0))
        VBox.setMargin(bigTimer, Insets(0.0, 0.0, 10.0, 0.0))

        // Build a VBox of all required components
        val rootBox = VBox()
        rootBox.children.addAll(
            splitHeader, attemptCounter, splitBody, bigTimer, sumOfBest
        )

        // Encase it in a root container
        // This handles foreground and background color
        // and other text properties like font and font size
        val root = StackPane()
        root.children.add(rootBox)

        // FIXME - migrate the styling to css
        //root.setStyle("-fx-background-color: rgb${Config.BACKGROUND_COLOR};")

        root.setPrefWidth(Config.LAUNCH_WIDTH)

        // Create and launch the primary scene
        val scene = Scene(root)
        primaryStage.scene = scene
        primaryStage.title = "SprintLux"
        primaryStage.isAlwaysOnTop = true
        primaryStage.show()
    }

    override fun stop() {
        inputProcessor.stopPolling()

        // FIXME - move this value elsewhere
        splitProvider.save(Config.SPLIT_FILE)
    }

    // Callbacks and split updates
    override fun onInputChanged(newInput: InputState?) {
        // TODO: Process new input
        // TODO: Handle global hotkeys
        logger.debug("Application detected input of $newInput")
        when (newInput) {
            InputState.SPLIT -> split()
            InputState.RESET -> reset()
            else -> return
        }
    }

    fun split() {
        val currentTime = Instant.now()

        val splits = splitProvider.splitFile

        // Handle the first split
        if (currentSplit == -1) {
            startTime.value = currentTime
           
            splits.value?.incrementAttemptCounter()
            attemptCounter.notifyNewAttemptCount()
            currentSplit ++

            lastSplitTime = currentTime
        } else if (currentSplit < splits.value?.splits?.size ?: -1) {
            val splitTime = Duration.between(lastSplitTime, currentTime).toMillis()
            
            // Handle golds
            // FIXME - need some way to set the split color to gold
            val goldTime = splits.value?.splits?.get(currentSplit)?.goldTimeMillis
            if (goldTime != null && splitTime < goldTime) {
                splits.value?.setGoldTime(currentSplit, splitTime)
                sumOfBest.updateSobLabel()
                logger.info("New gold! Split time = ${splits.value?.splits?.get(currentSplit)?.goldTimeMillis} ms")
            }

            // Handle split deltas
            splitBody.notifySplit(currentSplit, currentTime)

            currentSplit ++
            lastSplitTime = currentTime

            // FIXME - stop the big timer if this is the last split
        } else {
            // FIXME - if the current split is the last one
            // this should reset the timer
            // and update all splits to PB if needed
        }
    }

    fun reset() {
        startTime.value = null

        // FIXME - reset the split data as well
        // (including potential PBs)
    }

    
}

fun main() {
    Application.launch(Main::class.java)
}