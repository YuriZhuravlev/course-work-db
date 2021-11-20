package data.model

import java.util.*

/**
 * Научная публикация
 * @param postGraduate имя аспиранта
 * @param scientificDirector имя руководителя
 */
data class ScientificPublication(
    val id: Long,
    val name: String,
    val date: Date,
    val postGraduate: String,
    val scientificDirector: String
)
