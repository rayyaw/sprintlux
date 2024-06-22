package tech.rayyaw.sprintlux

import com.google.inject.Guice
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.stage.Stage
import javax.inject.Inject
import org.slf4j.Logger
import tech.rayyaw.sprintlux.input.InputProcessor

class Main: Application() {
    @Inject
    lateinit var logger: Logger

    @Inject
    lateinit var inputProcessor: InputProcessor

    init {
        val injector = Guice.createInjector(AppModule())
        injector.injectMembers(this)
    }

    override fun start(primaryStage: Stage) {
        inputProcessor.startPolling()

        val label = Label("Hello, JavaFX with Kotlin!")
        val scene = Scene(label, 400.0, 200.0)
        primaryStage.scene = scene
        primaryStage.title = "JavaFX Example"
        primaryStage.isAlwaysOnTop = true
        primaryStage.show()
    }
}

fun main() {
    Application.launch(Main::class.java)
}