package data.db.entity

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption

/**
 * Научный руководитель
 */
class DBScientificDirector(id: EntityID<Long>): LongEntity(id) {
    var name by DirectorTable.name
    var surname by DirectorTable.surname
    var cathedraId by DirectorTable.cathedraId

    companion object : LongEntityClass<DBScientificDirector>(DirectorTable)
}

object DirectorTable: LongIdTable("director", "director_id") {
    val name = varchar("director_name", 50)
    val surname = varchar("director_surname", 50)
    val cathedraId = long("director_cathedra_id").references(CathedraTable.id, onDelete = ReferenceOption.CASCADE)
}
