package stock.me.symbols.domain

import kotlinx.serialization.Serializable

@Serializable
data class HistoricalPriceResponse(
    val historicalPriceList: List<HistoricalPrice>
)
