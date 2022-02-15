package stock.me.provider.response

data class  StockDto(
    val symbol: String,
    val companyName: String,
    val stockQuote: StockQuoteDto,
    val stockStats: StockStatsDto
)