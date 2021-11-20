package data.model

import java.util.*

/**
 * Награда
 * @param postGraduate имя аспиранта
 */
data class Reward(
    val id: Long,
    val name: String,
    val date: Date,
    val postGraduate: String
)
