package data.db

/**
 * Научный руководитель
 */
data class DBScientificDirector(
    val id: Long,
    val name: String,
    val cathedraId: Long,
    val surname: String
)
