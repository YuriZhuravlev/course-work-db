package data.db.entity

data class DBReport(
    val name: String,
    val count: Int
) {
    companion object {
        const val NAME = "field_name"
        const val COUNT = "field_count"
    }
}
