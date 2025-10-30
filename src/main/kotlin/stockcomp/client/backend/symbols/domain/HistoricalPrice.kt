package stockcomp.client.backend.symbols.domain

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HistoricalPrice(
    val price: Double,
    @SerialName("price_date")
    val date: LocalDate,
)
