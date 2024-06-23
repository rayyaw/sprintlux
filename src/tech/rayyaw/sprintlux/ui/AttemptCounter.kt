package tech.rayyaw.sprintlux.ui

import javax.inject.Inject
import tech.rayyaw.sprintlux.split.SplitProvider
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.geometry.Pos

// Display the attempt counter
class AttemptCounter @Inject constructor(
    private val splitProvider: SplitProvider,
): VBox() {
    init {
        val splits = splitProvider.splitFile.value

        val label = Label(splits.attemptCounter.toString())

        alignment = Pos.CENTER_RIGHT
        children.add(label)
    }
}