package stock.me.service.mapper

import stock.me.service.response.StockQuoteDto
import yahoofinance.quotes.stock.StockQuote

fun toStockQuoteDto(stockQuote: StockQuote) : StockQuoteDto {
    return StockQuoteDto(
        price = stockQuote.price.toDouble(),
        previousClose = stockQuote.previousClose.toDouble(),
        dayLow = stockQuote.dayLow.toDouble(),
        dayHigh = stockQuote.dayHigh.toDouble(),
        openPrice = stockQuote.open.toDouble()
    )
}