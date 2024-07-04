package tech.rayyaw.sprintlux.split

import com.google.gson.annotations.Expose

data class SplitFile(
    val gameName: String,
    val categoryName: String,

    var attemptCounter: Int,

    var splits: MutableList<SingleSplit>,
) {
    fun incrementAttemptCounter() {
        attemptCounter ++
    }

    fun setGoldTime(split: Int, goldTime: Long) {
        splits[split].goldTimeMillis = goldTime
    }
}

data class SingleSplit(
    var splitName: String,
    
    // PB times are for the full run (cumulative), gold times are per segment
    var pbTimeMillis: Long,
    var goldTimeMillis: Long,

    // Not saved to disk
    @Expose var pbDeltaMillis: Long? = null,
)