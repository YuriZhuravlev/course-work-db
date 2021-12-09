package data.db.entity

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption

/**
 * Диплом
 */
class DBDiploma(id: EntityID<Long>): LongEntity(id) {
    var name by DiplomaTable.name
    var postGraduateId by DiplomaTable.postGraduateId
    var protectionId by DiplomaTable.protectionId

    companion object : LongEntityClass<DBDiploma>(DiplomaTable)
}

object DiplomaTable: LongIdTable("diploma", "diploma_id") {
    val name: Column<String> = varchar("diploma_name", 50)
    val postGraduateId =
        long("diploma_post_graduate_id").references(PostGraduateTable.id, onDelete = ReferenceOption.CASCADE)
    val protectionId = long("diploma_protection_id").references(ProtectionTable.id, onDelete = ReferenceOption.CASCADE)
}
