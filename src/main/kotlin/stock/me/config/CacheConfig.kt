package stock.me.config

import org.ehcache.CacheManager
import org.ehcache.config.builders.CacheConfigurationBuilder
import org.ehcache.config.builders.CacheManagerBuilder
import org.ehcache.config.builders.ExpiryPolicyBuilder
import org.ehcache.config.builders.ResourcePoolsBuilder
import yahoofinance.Stock
import java.time.Duration

fun getCacheManager(): CacheManager =
    CacheManagerBuilder.newCacheManagerBuilder()
        .withCache(
            "historicQuotes", CacheConfigurationBuilder
                .newCacheConfigurationBuilder(String::class.java, Stock::class.java, ResourcePoolsBuilder.heap(10))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofDays(1)))
        )
        .build()
        .apply { init() }