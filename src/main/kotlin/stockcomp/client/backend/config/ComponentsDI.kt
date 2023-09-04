package stockcomp.client.backend.config

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import stockcomp.client.backend.symbols.populate.consumer.FinnHubConsumer
import stockcomp.client.backend.symbols.populate.consumer.SymbolPopulatorConsumer
import stockcomp.client.backend.symbols.populate.service.DefaultSymbolPopulatorService
import stockcomp.client.backend.symbols.populate.service.SymbolPopulatorService
import stockcomp.client.backend.symbols.search.consumer.FastFinanceConsumer
import stockcomp.client.backend.symbols.search.consumer.SymbolSearchConsumer
import stockcomp.client.backend.symbols.search.service.DefaultSymbolSearchService
import stockcomp.client.backend.symbols.search.service.SymbolSearchService
import stockcomp.client.backend.symbols.trending.service.DefaultTrendingSymbolsService
import stockcomp.client.backend.symbols.trending.service.TrendingSymbolsService

/**
 * Make components available with dependency injection using Kodein
 */
val kodein = DI {
    bindSingleton { getCacheManager() }

    bindSingleton { getRedisClient() }

    bindSingleton<SymbolPopulatorConsumer> { FinnHubConsumer(HttpClient.client) }

    bindSingleton<SymbolPopulatorService> { DefaultSymbolPopulatorService(instance(), instance()) }

    bindSingleton<TrendingSymbolsService> { DefaultTrendingSymbolsService(instance()) }

    bindSingleton<SymbolSearchConsumer> { FastFinanceConsumer(HttpClient.client, "http://fastfinance-service:8000") }

    bindSingleton<SymbolSearchService> { DefaultSymbolSearchService(instance(), instance(), instance()) }
}
