package stock.me.symbols.domain

import kotlinx.serialization.Serializable

@Serializable
data class SymbolFinancials(
    val symbol: String,
    val companyName: String,
    val currency: String,
    val marketCap: Long,
    val priceToBook: Double,
    val priceToEarningsRatio: Double,
    val earningsPerShare: Double,
    val dividendRate: Double?,
    val dividendYieldPercentage: Double?,
)
