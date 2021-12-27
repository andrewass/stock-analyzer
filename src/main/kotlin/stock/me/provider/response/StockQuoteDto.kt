package stock.me.provider.response

import kotlinx.serialization.Serializable

@Serializable
data class StockQuoteDto(
    val symbol : String,
    val name : String,
    val price: Double,
    val openPrice: Double,
    val previousClose: Double,
    val dayLow: Double,
    val dayHigh: Double,
    val usdPrice: Double,
    val currency : String
)
