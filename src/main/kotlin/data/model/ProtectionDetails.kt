package data.model

import java.util.*

/**
 * Защита
 */
data class ProtectionDetails(
    val id: Long,
    val name: String,
    val date: Date,
    val diploma: DiplomaDetails,
)
