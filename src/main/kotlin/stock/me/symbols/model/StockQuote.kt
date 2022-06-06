package stock.me.symbols.model

import kotlinx.serialization.Serializable

@Serializable
data class StockQuote(
    val symbol : String,
    val name : String,
    val price: Double,
    val priceChange: Double,
    val usdPrice: Double,
    val currency : String
)