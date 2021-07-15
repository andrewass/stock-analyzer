package stock.me.service.response

import kotlinx.serialization.Serializable

@Serializable
data class StockStatsDto(
    val marketCap: Long?,
    val sharesOutstanding: Long?,
    val earningsPerShare: Double?,
    val priceToEarnings: Double?,
    val priceToEarningsGrowth: Double?,
    val priceToBook: Double?,
    val priceToSales: Double?,
    val bookValuePerShare : Double?,
    val revenue : Long?
)