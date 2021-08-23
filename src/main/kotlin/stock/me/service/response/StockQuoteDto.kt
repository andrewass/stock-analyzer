package stock.me.service.response

import kotlinx.serialization.Serializable

@Serializable
data class StockQuoteDto(
    val price: Double,
    val openPrice: Double,
    val previousClose: Double,
    val dayLow: Double,
    val dayHigh: Double,
    val usdPrice: Double,
    val currency : String
)
