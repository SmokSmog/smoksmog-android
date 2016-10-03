package pl.malopolska.smoksmog.model

import org.joda.time.LocalDate

data class History(
        val value: Float,
        val date: LocalDate) {
}
