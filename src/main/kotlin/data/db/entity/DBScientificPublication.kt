package data.db.entity

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.date

/**
 * Научная публикация
 */
class DBScientificPublication(id: EntityID<Long>): LongEntity(id) {
    val name by PublicationTable.name
    val date by PublicationTable.date
    val postGraduateId by PublicationTable.postGraduateId

    companion object: LongEntityClass<DBScientificPublication>(PublicationTable)
}

object PublicationTable: LongIdTable("publication", "publication_id") {
    val name = varchar("publication_name", 50)
    val date = date("publication_date")
    val postGraduateId =
        long("publication_post_graduate_id").references(PostGraduateTable.id, onDelete = ReferenceOption.CASCADE)
}
