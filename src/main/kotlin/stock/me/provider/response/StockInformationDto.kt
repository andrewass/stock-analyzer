package stock.me.provider.response

data class StockInformationDto(
    val annualDividendYieldPercent : Double?,
    val priceToEarnings : Double?,
    val earningsPerShare : Double?,
    val priceToBook: Double?,
    val marketCap: Long?,
    val sharesOwned: Long?,
    val revenue: Long?,
    val shortRatio: Double?
)
