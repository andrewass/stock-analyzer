package stock.me.symbols.domain

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class HistoricalQuote(
    val price: Double,
    val quoteDate: LocalDate,
    val volume: Long
)