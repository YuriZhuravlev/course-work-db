package data.db.entity

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

/**
 * Кафедра
 */
class DBCathedra(id: EntityID<Long>) : LongEntity(id) {
    var name by CathedraTable.name

    companion object: LongEntityClass<DBCathedra>(CathedraTable)
}

object CathedraTable: LongIdTable("cathedra", "cathedra_id") {
    val name: Column<String> = varchar("cathedra_name", 50)
}

