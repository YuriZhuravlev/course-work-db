package data.model

import java.time.LocalDate

/**
 * Научная публикация
 */
data class ScientificPublication(
    val id: Long,
    val name: String,
    val date: LocalDate,
    val postGraduateId: Long
)
