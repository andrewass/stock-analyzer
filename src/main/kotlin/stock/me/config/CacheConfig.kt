package stock.me.config

import org.ehcache.CacheManager
import org.ehcache.config.builders.CacheConfigurationBuilder
import org.ehcache.config.builders.CacheManagerBuilder
import org.ehcache.config.builders.ExpiryPolicyBuilder
import org.ehcache.config.builders.ResourcePoolsBuilder
import stock.me.symbols.domain.HistoricalPriceResponse
import stock.me.symbols.search.service.CacheKey
import java.time.Duration

fun getCacheManager(): CacheManager =
    CacheManagerBuilder.newCacheManagerBuilder()
        .withCache(
            "historicQuotes", CacheConfigurationBuilder
                .newCacheConfigurationBuilder(CacheKey::class.java, HistoricalPriceResponse::class.java, ResourcePoolsBuilder.heap(1000))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofDays(1)))
        )
        .withCache(
            "stockInformation", CacheConfigurationBuilder
                .newCacheConfigurationBuilder(String::class.java, HistoricalPriceResponse::class.java, ResourcePoolsBuilder.heap(1000))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofDays(1)))
        )
        .build()
        .apply { init() }