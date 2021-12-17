package data.model

import java.time.LocalDate
import java.util.*

/**
 * Защита
 */
data class Protection(
    val id: Long,
    val councilId: Long,
    val date: LocalDate
)
