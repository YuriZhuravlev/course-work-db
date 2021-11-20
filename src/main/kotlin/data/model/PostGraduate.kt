package data.model

/**
 * Аспирант
 */
data class PostGraduate(
    val id: Long,
    val name: String,
    val surname: String,
    val cathedra: String,
//    val scientificDirector: ScientificDirector,
    val scientificDirection: String,
    val category: String
)
