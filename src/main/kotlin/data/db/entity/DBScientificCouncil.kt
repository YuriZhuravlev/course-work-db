package data.db.entity

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

/**
 * Научный совет
 */
class DBScientificCouncil(id: EntityID<Long>): LongEntity(id) {
    var name by CouncilTable.name

    companion object: LongEntityClass<DBScientificCouncil>(CouncilTable)
}

object CouncilTable: LongIdTable("council", "council_id") {
    val name = varchar("council_name", 50)
}
