package tech.rayyaw.sprintlux.ui

import javax.inject.Inject
import javafx.stage.Stage
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.geometry.Pos
import tech.rayyaw.sprintlux.split.SplitProvider

// Displays the split header
// which is static information containing the game title and category.
class SplitHeader @Inject constructor(
    private val splitProvider: SplitProvider
): VBox() {
    init {
        val splits = splitProvider.splitFile.value

        // FIXME - this needs to update dynamically
        splits?.let {
            val titleLabel = Label(splits.gameName)
            val subtitleLabel = Label(splits.categoryName)

            alignment = Pos.CENTER

            children.addAll(titleLabel, subtitleLabel)
        }
    }
}