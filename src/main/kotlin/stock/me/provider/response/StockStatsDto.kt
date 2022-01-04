package stock.me.provider.response

data class StockStatsDto(
    val annualDividendYieldPercent: Double?,
    val priceToEarnings: Double?,
    val earningsPerShare: Double?,
    val priceToBook: Double?,
    val marketCap: Double?,
    val sharesOwned: Long?,
    val revenue: Double?,
    val shortRatio: Double?
)
