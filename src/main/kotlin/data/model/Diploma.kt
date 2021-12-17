package data.model

/**
 * Диплом
 */
data class Diploma(
    val id: Long,
    val name: String,
    val postGraduateId: Long,
    val protectionId: Long,
)
