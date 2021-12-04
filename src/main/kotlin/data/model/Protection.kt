package data.model

import java.util.*

/**
 * Защита
 */
data class Protection(
    val id: Long,
    val name: String,
    val councilId: Long,
    val date: Date
)
