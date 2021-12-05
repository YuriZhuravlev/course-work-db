package data.model

/**
 * Научный руководитель
 */
data class ScientificDirectorDetails(
    val id: Long,
    val name: String,
    val surname: String,
    val cathedra: Cathedra?
)
