package data.db.entity

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.date

/**
 * Награда
 */
class DBReward(id: EntityID<Long>) :LongEntity(id) {
    var name by RewardTable.name
    var date by RewardTable.date
    var postGraduateId by RewardTable.postGraduateId

    companion object: LongEntityClass<DBReward>(RewardTable)
}

object RewardTable: LongIdTable("reward", "reward_id") {
    val name = varchar("reward_name", 50)
    val date = date("reward_date")
    val postGraduateId =
        long("reward_post_graduate_id").references(PostGraduateTable.id, onDelete = ReferenceOption.CASCADE)
}
