package tech.rayyaw.sprintlux

import com.google.inject.Guice
import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.stage.Stage
import javax.inject.Inject
import org.slf4j.Logger
import tech.rayyaw.sprintlux.config.Config
import tech.rayyaw.sprintlux.input.InputDelegate
import tech.rayyaw.sprintlux.input.InputProcessor
import tech.rayyaw.sprintlux.input.InputState
import tech.rayyaw.sprintlux.ui.SplitHeader
import tech.rayyaw.sprintlux.ui.AttemptCounter
import tech.rayyaw.sprintlux.ui.SplitBody
import tech.rayyaw.sprintlux.ui.BigTimer
import tech.rayyaw.sprintlux.ui.SumOfBest

// TODO: Dynamic updating (ie, don't show a static screen), edit config via right click menu
class Main: Application(), InputDelegate {
    @Inject lateinit var logger: Logger
    @Inject lateinit var inputProcessor: InputProcessor

    // Views
    @Inject lateinit var splitHeader: SplitHeader
    @Inject lateinit var attemptCounter: AttemptCounter
    @Inject lateinit var splitBody: SplitBody
    @Inject lateinit var bigTimer: BigTimer
    @Inject lateinit var sumOfBest: SumOfBest

    init {
        val injector = Guice.createInjector(AppModule())
        injector.injectMembers(this)
    }

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
        root.setStyle("-fx-background-color: rgb${Config.BACKGROUND_COLOR};")

        root.setPrefWidth(Config.LAUNCH_WIDTH)

        // Create and launch the primary scene
        val scene = Scene(root)
        primaryStage.scene = scene
        primaryStage.title = "SprintLux"
        primaryStage.isAlwaysOnTop = true
        primaryStage.show()
    }

    override fun stop() {
        // FIXME - auto-save split file on closure
        inputProcessor.deregisterDelegate(this)
        inputProcessor.stopPolling()
    }

    // extension Main: InputDelegate
    override fun onInputChanged(newInput: InputState) {
        // TODO: Process new input
    }
}

fun main() {
    Application.launch(Main::class.java)
}