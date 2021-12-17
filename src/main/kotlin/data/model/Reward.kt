package data.model

import java.time.LocalDate

/**
 * Награда
 */
data class Reward(
    val id: Long,
    val name: String,
    val date: LocalDate,
    val postGraduateId: Long,
)
