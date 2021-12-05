package data.db.entity

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

/**
 * Категория
 */
class DBCategory(id: EntityID<Long>) : LongEntity(id) {
    var name by CategoryTable.name

    companion object : LongEntityClass<DBCategory>(CategoryTable)
}

object CategoryTable : LongIdTable("category", "category_id") {
    val name: Column<String> = varchar("category_name", 50)
}
