package stock.me.provider.response

data class StockDto(
    val symbol: String,
    val stockQuote: StockQuoteDto,
    val stockInformation: StockInformationDto
)