package tech.rayyaw.sprintlux.split

import com.google.gson.Gson
import com.google.inject.Singleton
import java.io.File
import java.io.FileWriter
import javax.inject.Inject
import tech.rayyaw.sprintlux.config.Config
import kotlinx.coroutines.flow.MutableStateFlow

@Singleton
class SplitProvider @Inject constructor(
    private val jsonParser: Gson,
) {
    val splitFile: MutableStateFlow<SplitFile?> = MutableStateFlow(null)

    init {
        load(Config.SPLIT_FILE)
    }

    fun load(splitFile: String) {
        val fileData = File(splitFile).readText()
        this.splitFile.value = jsonParser.fromJson(
            fileData, SplitFile::class.java
        )
    }

    fun save(splitFile: String) {
        this.splitFile.value?.let { splits ->
            val data = jsonParser.toJson(splits)
            FileWriter(splitFile).use { fileWriter ->
                fileWriter.write(data)
            }
        }
    }

}