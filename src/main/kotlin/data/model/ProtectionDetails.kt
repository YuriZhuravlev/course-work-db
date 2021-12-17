package data.model

import java.time.LocalDate

/**
 * Защита
 */
data class ProtectionDetails(
    val id: Long,
    val councilId: Long,
    val date: LocalDate,
    val diploma: Diploma?
)
