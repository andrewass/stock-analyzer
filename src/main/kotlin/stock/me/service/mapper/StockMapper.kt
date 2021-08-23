package stock.me.service.mapper

import kotlinx.datetime.toKotlinLocalDate
import stock.me.model.Currency
import stock.me.service.response.HistoricalQuoteDto
import stock.me.service.response.StockQuoteDto
import stock.me.service.response.StockStatsDto
import yahoofinance.histquotes.HistoricalQuote
import yahoofinance.quotes.stock.StockQuote
import yahoofinance.quotes.stock.StockStats
import java.time.LocalDate
import java.time.ZoneId

fun toStockQuoteDto(stockQuote: StockQuote, currency: Currency, usdPrice: Double) =
    StockQuoteDto(
        price = stockQuote.price.toDouble(),
        previousClose = stockQuote.previousClose.toDouble(),
        dayLow = stockQuote.dayLow.toDouble(),
        dayHigh = stockQuote.dayHigh.toDouble(),
        openPrice = stockQuote.open.toDouble(),
        currency = currency.name,
        usdPrice = usdPrice
    )

fun toStockStatsDto(stockStats: StockStats) =
    StockStatsDto(
        priceToBook = stockStats.priceBook?.toDouble(),
        priceToEarnings = stockStats.pe?.toDouble(),
        priceToEarningsGrowth = stockStats.peg?.toDouble(),
        priceToSales = stockStats.priceSales?.toDouble(),
        bookValuePerShare = stockStats.bookValuePerShare?.toDouble(),
        earningsPerShare = stockStats.eps?.toDouble(),
        revenue = stockStats.revenue?.toLong(),
        sharesOutstanding = stockStats.sharesOutstanding,
        marketCap = stockStats.marketCap?.toLong()
    )

fun toHistoricalPriceDto(historicalQuote: HistoricalQuote) =
    HistoricalQuoteDto(
        price = historicalQuote.close.toDouble(),
        volume = historicalQuote.volume,
        date = LocalDate.ofInstant(historicalQuote.date.toInstant(), ZoneId.systemDefault())
            .toKotlinLocalDate()
    )
