package tech.rayyaw.sprintlux.split

data class SplitFile(
    val gameName: String,
    val categoryName: String,

    val attemptCounter: Int,

    val splits: List<SingleSplit>,
)

data class SingleSplit(
    val splitName: String,
    
    // PB times are for the full run (cumulative), gold times are per segment
    val pbTimeMillis: Long,
    val goldTimeMillis: Long,
    val pbDeltaMillis: Long? = null, // Not saved to disk
)