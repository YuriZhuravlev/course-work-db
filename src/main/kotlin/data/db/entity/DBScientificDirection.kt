package data.db.entity

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

/**
 * Научное направление
 */
class DBScientificDirection(id: EntityID<Long>): LongEntity(id) {
    var name by DirectionTable.name

    companion object: LongEntityClass<DBScientificDirection>(DirectionTable)
}

object DirectionTable: LongIdTable("direction", "direction_id") {
    val name = varchar("direction_name", 50)
}
