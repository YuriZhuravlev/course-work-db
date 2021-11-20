package data.db

import data.model.Protection

/**
 * Диплом
 */
data class DBDiploma(
    val id: Long,
    val name: String,
    val postGraduateId: Long,
    val protectionId: Long
)
