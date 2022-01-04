package stock.me.provider

import kotlinx.datetime.toKotlinLocalDate
import stock.me.model.Currency
import stock.me.provider.response.HistoricalQuoteDto
import stock.me.provider.response.StockDto
import stock.me.provider.response.StockStatsDto
import stock.me.provider.response.StockQuoteDto
import yahoofinance.Stock
import yahoofinance.YahooFinance
import java.time.LocalDate
import java.time.ZoneId

fun toStockDto(stock: Stock) = StockDto(
    symbol = stock.symbol,
    stockQuote = toStockQuoteDto(stock),
    stockStats = toStockInformationDto(stock)
)

fun toStockQuoteDto(stock: Stock): StockQuoteDto {
    val currency = Currency.valueOf(stock.currency)
    val usdPrice = getUsdPrice(stock.quote.price.toDouble(), currency)

    return mapToStockQuoteDto(stock, currency, usdPrice)
}

private fun toStockInformationDto(stock: Stock) = StockStatsDto(
    annualDividendYieldPercent = stock.dividend?.annualYieldPercent?.toDouble(),
    earningsPerShare = stock.stats?.eps?.toDouble(),
    marketCap = stock.stats?.marketCap?.toDouble(),
    priceToBook = stock.stats?.priceBook?.toDouble(),
    priceToEarnings = stock.stats?.pe?.toDouble(),
    revenue = stock.stats?.revenue?.toDouble(),
    sharesOwned = stock.stats?.sharesOwned,
    shortRatio = stock.stats?.shortRatio?.toDouble()
)

fun toHistoricalQuoteDto(stock: Stock): List<HistoricalQuoteDto> =
    stock.history
        .filter { it.close != null }
        .map {
            HistoricalQuoteDto(
                price = it.close.toDouble(),
                volume = it.volume,
                date = LocalDate.ofInstant(it.date.toInstant(), ZoneId.systemDefault()).toKotlinLocalDate()
            )
        }


private fun mapToStockQuoteDto(stock: Stock, currency: Currency, usdPrice: Double): StockQuoteDto {
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

private fun getUsdPrice(price: Double, currency: Currency): Double {
    return if (currency == Currency.USD) {
        price
    } else {
        val fxQuote = YahooFinance.getFx(currency.forexCode).price.toDouble()
        price / fxQuote
    }
}