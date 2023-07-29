package stock.me.symbols.search.consumer.request

import kotlinx.serialization.Serializable

@Serializable
data class CurrentPriceResponse(
    val symbol: String,
    val companyName: String,
    val currentPrice: Double,
    val previousClose: Double,
    val currency: String,
)
