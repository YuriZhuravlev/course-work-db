package data.model

/**
 * Аспирант
 */
data class PostGraduate(
    val id: Long,
    val name: String,
    val surname: String,
    val scientificDirectorId: Long,
    val scientificDirectionId: Long,
    val categoryId: Long,
)
