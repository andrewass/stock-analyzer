package stock.me.symbols.search.service

import org.ehcache.CacheManager
import org.kodein.di.instance
import stock.me.config.kodein
import stock.me.symbols.domain.HistoricalPriceResponse


private val cacheManager by kodein.instance<CacheManager>()

private val historicQuotesCache =
    cacheManager.getCache("historicQuotes", String::class.java, HistoricalPriceResponse::class.java)

fun getHistoricalQuotesCache(symbol: String): HistoricalPriceResponse? = historicQuotesCache.get(symbol)

fun addHistoricalQuotesCache(symbol: String, response: HistoricalPriceResponse) {
    historicQuotesCache.put(symbol, response)
}