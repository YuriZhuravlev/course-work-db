package utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun LocalDate?.tryParse(dateString: String): LocalDate? {
    var date: LocalDate? = null
    try {
        date = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE)
    } catch (_: Exception) {
    }
    return date
}
