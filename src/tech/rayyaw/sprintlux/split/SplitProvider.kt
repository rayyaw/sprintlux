package tech.rayyaw.sprintlux.split

import com.google.gson.Gson
import javax.inject.Inject
import java.io.File
import tech.rayyaw.sprintlux.config.Config
import kotlinx.coroutines.flow.MutableStateFlow

class SplitProvider @Inject constructor(
    private val jsonParser: Gson,
) {
    val splitFile: MutableStateFlow<SplitFile>

    init {
        // TODO: This needs to be configurable
        val fileData = File(Config.SPLIT_FILE).readText()
        splitFile = MutableStateFlow(
            jsonParser.fromJson(fileData, SplitFile::class.java)
        ) 
    }

}