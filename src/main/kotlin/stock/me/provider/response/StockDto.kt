package stock.me.provider.response

data class StockDto(
    val symbol: String,
    val stockQuoteDto: StockQuoteDto,
    val stockInformationDto: StockInformationDto
)