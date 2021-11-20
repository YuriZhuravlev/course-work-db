package data.db

import java.util.*

/**
 * Научная публикация
 */
data class DBScientificPublication(
    val id: Long,
    val name: String,
    val date: Date,
    val postGraduateId: Long
)
