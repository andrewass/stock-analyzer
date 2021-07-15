package stock.me.service.mapper

import kotlinx.datetime.toKotlinLocalDate
import stock.me.service.response.HistoricalQuoteDto
import stock.me.service.response.StockQuoteDto
import yahoofinance.histquotes.HistoricalQuote
import yahoofinance.quotes.stock.StockQuote
import java.time.LocalDate
import java.time.ZoneId

fun toStockQuoteDto(stockQuote: StockQuote) =
    StockQuoteDto(
        price = stockQuote.price.toDouble(),
        previousClose = stockQuote.previousClose.toDouble(),
        dayLow = stockQuote.dayLow.toDouble(),
        dayHigh = stockQuote.dayHigh.toDouble(),
        openPrice = stockQuote.open.toDouble()
    )

fun toHistoricalPriceDto(historicalQuote: HistoricalQuote) =
    HistoricalQuoteDto(
        price = historicalQuote.close.toDouble(),
        volume = historicalQuote.volume,
        date = LocalDate.ofInstant(historicalQuote.date.toInstant(), ZoneId.systemDefault())
            .toKotlinLocalDate()
    )