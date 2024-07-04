package tech.rayyaw.sprintlux.ui

import javax.inject.Inject
import javafx.application.Platform
import javafx.beans.property.SimpleIntegerProperty
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.geometry.Pos
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import tech.rayyaw.sprintlux.split.SplitProvider
import tech.rayyaw.sprintlux.AppModule

// Display the attempt counter
class AttemptCounter @Inject constructor(
    private val splitProvider: SplitProvider,
    private val coroutineScope: CoroutineScope,
): VBox() {

    private val attemptCounterProperty = SimpleIntegerProperty(0)

    init {
        alignment = Pos.CENTER_RIGHT

        val splits = splitProvider.splitFile.value
        attemptCounterProperty.set(splits?.attemptCounter ?: 0)

        splits?.attemptCounter?.let {
            val label = Label()
            label.textProperty().bind(attemptCounterProperty.asString())

            children.add(label)
        }

        // Listen for changes in splitProvider.splitFile.value
        coroutineScope.launch {
            splitProvider.splitFile.collect { newSplits ->
                attemptCounterProperty.set(newSplits?.attemptCounter ?: 0)
            }
        }
    }

    fun notifyNewAttemptCount() {
        Platform.runLater {
            val splits = splitProvider.splitFile.value
            attemptCounterProperty.set(splits?.attemptCounter ?: 0)
        }
    }
}