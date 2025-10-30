package stockcomp.client.backend.symbols.search.service

import org.ehcache.CacheManager
import org.kodein.di.instance
import stockcomp.client.backend.config.kodein
import stockcomp.client.backend.symbols.domain.HistoricalPriceResponse

private val cacheManager by kodein.instance<CacheManager>()

private val historicQuotesCache =
    cacheManager.getCache("historicQuotes", CacheKey::class.java, HistoricalPriceResponse::class.java)

fun getHistoricalQuotesCache(cacheKey: CacheKey): HistoricalPriceResponse? = historicQuotesCache.get(cacheKey)

fun addHistoricalQuotesCache(
    cacheKey: CacheKey,
    response: HistoricalPriceResponse,
) {
    historicQuotesCache.put(cacheKey, response)
}
