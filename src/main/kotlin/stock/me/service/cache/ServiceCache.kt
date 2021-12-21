package stock.me.service.cache

import org.ehcache.CacheManager
import org.kodein.di.instance
import stock.me.config.kodein
import yahoofinance.Stock
import yahoofinance.histquotes.HistoricalQuote


private val cacheManager by kodein.instance<CacheManager>()

private val cache = cacheManager.getCache("historicQuotes", String::class.java, Stock::class.java)

fun getHistoricalQuotesCache(symbol: String): List<HistoricalQuote>? = cache.get(symbol)?.history

fun addHistoricalQuotesCache(symbol: String, stock: Stock) {
    cache.put(symbol, stock)
}