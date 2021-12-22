package stock.me.service.cache

import org.ehcache.CacheManager
import org.kodein.di.instance
import stock.me.config.kodein
import yahoofinance.Stock
import yahoofinance.histquotes.HistoricalQuote


private val cacheManager by kodein.instance<CacheManager>()

private val historicQuotesCache = cacheManager.getCache("historicQuotes", String::class.java, Stock::class.java)

fun getHistoricalQuotesCache(symbol: String): List<HistoricalQuote>? = historicQuotesCache.get(symbol)?.history

fun addHistoricalQuotesCache(symbol: String, stock: Stock) {
    historicQuotesCache.put(symbol, stock)
}