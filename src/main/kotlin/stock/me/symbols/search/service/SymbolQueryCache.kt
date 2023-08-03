package stock.me.symbols.search.service

import org.ehcache.CacheManager
import org.kodein.di.instance
import stock.me.config.kodein
import stock.me.symbols.domain.HistoricalPriceResponse


private val cacheManager by kodein.instance<CacheManager>()

private val historicQuotesCache =
    cacheManager.getCache("historicQuotes", CacheKey::class.java, HistoricalPriceResponse::class.java)

fun getHistoricalQuotesCache(cacheKey: CacheKey): HistoricalPriceResponse? = historicQuotesCache.get(cacheKey)

fun addHistoricalQuotesCache(cacheKey: CacheKey, response: HistoricalPriceResponse) {
    historicQuotesCache.put(cacheKey, response)
}