package stock.me.symbols.domain

import kotlinx.serialization.Serializable

@Serializable
data class StockQuote(
    val price: Double,
    val priceChange: Double,
    val percentageChange: Double,
    val usdPrice: Double,
    val currency : String
)