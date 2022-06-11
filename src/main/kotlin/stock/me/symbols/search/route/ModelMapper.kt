package stock.me.symbols.search.route

import kotlinx.datetime.toKotlinLocalDate
import stock.me.symbols.domain.*
import yahoofinance.Stock
import yahoofinance.YahooFinance
import java.time.LocalDate
import java.time.ZoneId

fun toStock(src: Stock) = Stock(
    symbol = src.symbol,
    description = src.name,
    stockQuote = toStockQuoteDto(src),
    stockStats = toStockInformationDto(src)
)

fun toStockQuoteDto(stock: Stock): StockQuote {
    val currency = Currency.valueOf(stock.currency)
    val usdPrice = getUsdPrice(stock.quote.price.toDouble(), currency)

    return mapToStockQuoteDto(stock, currency, usdPrice)
}

private fun toStockInformationDto(stock: Stock) = StockStats(
    annualDividendYieldPercent = stock.dividend?.annualYieldPercent?.toDouble(),
    earningsPerShare = stock.stats?.eps?.toDouble(),
    marketCap = stock.stats?.marketCap?.toDouble(),
    priceToBook = stock.stats?.priceBook?.toDouble(),
    priceToEarnings = stock.stats?.pe?.toDouble(),
    revenue = stock.stats?.revenue?.toDouble(),
    sharesOwned = stock.stats?.sharesOwned,
    shortRatio = stock.stats?.shortRatio?.toDouble()
)

fun toHistoricalQuoteDto(stock: Stock): List<HistoricalQuote> =
    stock.history
        .filter { it.close != null }
        .map {
            HistoricalQuote(
                price = it.close.toDouble(),
                volume = it.volume,
                quoteDate = LocalDate.ofInstant(it.date.toInstant(), ZoneId.systemDefault()).toKotlinLocalDate()
            )
        }


private fun mapToStockQuoteDto(stock: Stock, currency: Currency, usdPrice: Double): StockQuote {
    val quote = stock.quote

    return StockQuote(
        symbol = stock.symbol,
        name = stock.name,
        price = quote.price.toDouble(),
        priceChange = calculatePriceChange(stock),
        percentageChange = calculatePercentageChange(stock),
        currency = currency.name,
        usdPrice = usdPrice
    )
}

private fun calculatePercentageChange(stock: Stock): Double =
    if (stock.quote.previousClose != null) {
        (calculatePriceChange(stock) / stock.quote.previousClose.toDouble()) * 100
    } else {
        0.00
    }

private fun calculatePriceChange(stock: Stock): Double =
    stock.quote.price.toDouble() - (stock.quote.previousClose ?: 0).toDouble()


private fun getUsdPrice(price: Double, currency: Currency): Double {
    return if (currency == Currency.USD) {
        price
    } else {
        val fxQuote = YahooFinance.getFx(currency.forexCode).price.toDouble()
        price / fxQuote
    }
}