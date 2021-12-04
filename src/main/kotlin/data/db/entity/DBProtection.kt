package data.db.entity

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.`java-time`.date
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.util.*

/**
 * Защита
 */
class DBProtection(id: EntityID<Long>) : LongEntity(id) {
    val councilId by ProtectionTable.councilId
    val date by ProtectionTable.date

    companion object : LongEntityClass<DBProtection>(ProtectionTable)
}

object ProtectionTable : LongIdTable("protection", "protection_id") {
    val date = date("protection_date")
    val councilId = long("protection_council_id")
}
