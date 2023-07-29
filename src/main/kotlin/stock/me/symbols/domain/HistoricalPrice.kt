package stock.me.symbols.domain

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class HistoricalPrice(
    val price: Double,
    val date: LocalDate
)