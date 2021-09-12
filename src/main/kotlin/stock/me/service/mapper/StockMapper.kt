package stock.me.service.mapper

import kotlinx.datetime.toKotlinLocalDate
import stock.me.model.Currency
import stock.me.service.response.HistoricalQuoteDto
import stock.me.service.response.StockQuoteDto
import yahoofinance.Stock
import yahoofinance.histquotes.HistoricalQuote
import java.time.LocalDate
import java.time.ZoneId

fun toStockQuoteDto(stock: Stock, currency: Currency, usdPrice: Double): StockQuoteDto {
    val quote = stock.quote

    return StockQuoteDto(
        symbol = stock.symbol,
        name = stock.name,
        price = quote.price.toDouble(),
        previousClose = quote.previousClose.toDouble(),
        dayLow = quote.dayLow.toDouble(),
        dayHigh = quote.dayHigh.toDouble(),
        openPrice = quote.open.toDouble(),
        currency = currency.name,
        usdPrice = usdPrice
    )
}

fun toHistoricalPriceDto(historicalQuote: HistoricalQuote) =
    HistoricalQuoteDto(
        price = historicalQuote.close.toDouble(),
        volume = historicalQuote.volume,
        date = LocalDate.ofInstant(historicalQuote.date.toInstant(), ZoneId.systemDefault()).toKotlinLocalDate()
    )
