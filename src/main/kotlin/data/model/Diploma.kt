package data.model

/**
 * Диплом
 * @param postGraduate имя аспиранта
 */
data class Diploma(
    val id: Long,
    val name: String,
    val postGraduate: String,
    val protection: Protection
)
