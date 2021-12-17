package data.model

data class PostGraduateDetails(
    val id: Long,
    val name: String,
    val surname: String,
    val scientificDirector: ScientificDirectorDetails?,
    val scientificDirection: ScientificDirection?,
    val category: Category?
)
