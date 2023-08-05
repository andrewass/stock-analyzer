package stockcomp.client.backend.symbols.domain

import kotlinx.serialization.Serializable

@Serializable
data class HistoricalPriceResponse(
    val historicalPriceList: List<HistoricalPrice>
)
