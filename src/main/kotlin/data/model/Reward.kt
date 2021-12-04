package data.model

import java.util.*

/**
 * Награда
 */
data class Reward(
    val id: Long,
    val name: String,
    val date: Date,
    val postGraduateId: Long,
)
