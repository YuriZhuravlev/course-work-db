package data.db.entity

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption

/**
 * Аспирант
 */
class DBPostGraduate(id: EntityID<Long>): LongEntity(id) {
    val name by PostGraduateTable.name
    val surname by PostGraduateTable.surname
    val scientificDirectorId by PostGraduateTable.scientificDirectorId
    val scientificDirectionId by PostGraduateTable.scientificDirectionId
    val categoryId by PostGraduateTable.categoryId

    companion object: LongEntityClass<DBPostGraduate>(PostGraduateTable)
}

object PostGraduateTable: LongIdTable("post_graduate", "post_graduate_id") {
    val name = varchar("post_graduate_name", 50)
    val surname = varchar("post_graduate_surname", 50)
    val scientificDirectorId =
        long("post_graduate_director_id").references(DirectorTable.id, onDelete = ReferenceOption.CASCADE)
    val scientificDirectionId =
        long("post_graduate_direction_id").references(DirectionTable.id, onDelete = ReferenceOption.CASCADE)
    val categoryId = long("post_graduate_category_id").references(CategoryTable.id, onDelete = ReferenceOption.CASCADE)
}
