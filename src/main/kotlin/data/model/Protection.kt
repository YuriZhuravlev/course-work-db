package data.model

import java.util.*

/**
 * Защита
 * @param council название совета
 */
data class Protection(
    val id: Long,
    val name: String,
    val council: String,
    val date: Date
)
