package stock.me.service.cache

import org.ehcache.CacheManager
import org.kodein.di.instance
import stock.me.config.kodein
import yahoofinance.Stock


private val cacheManager by kodein.instance<CacheManager>()

private val historicQuotesCache =
    cacheManager.getCache("historicQuotes", String::class.java, Stock::class.java)

fun getHistoricalQuotesCache(symbol: String): Stock? = historicQuotesCache.get(symbol)

fun addHistoricalQuotesCache(symbol: String, stock: Stock) {
    historicQuotesCache.put(symbol, stock)
}