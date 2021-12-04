package data.db

/**
 * Аспирант
 */
data class DBPostGraduate(
    val id: Long,
    val name: String,
    val surname: String,
    val scientificDirectorId: Long,
    val scientificDirectionId: Long,
    val categoryId: Long
)
