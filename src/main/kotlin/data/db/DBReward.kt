package data.db

import java.util.*

/**
 * Награда
 */
data class DBReward(
    val id: Long,
    val name: String,
    val date: Date,
    val postGraduateId: Long
)
