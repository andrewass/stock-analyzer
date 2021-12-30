package stock.me.provider.response

data class StockDto(
    val stockQuoteDto: StockQuoteDto? = null,
    val stockInformationDto: StockInformationDto? = null

)