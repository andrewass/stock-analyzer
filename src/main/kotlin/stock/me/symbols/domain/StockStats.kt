package stock.me.symbols.domain

import kotlinx.serialization.Serializable

@Serializable
data class StockStats(
    val annualDividendYieldPercent: Double?,
    val priceToEarnings: Double?,
    val earningsPerShare: Double?,
    val priceToBook: Double?,
    val marketCap: Double?,
    val sharesOwned: Long?,
    val revenue: Double?,
    val shortRatio: Double?
)