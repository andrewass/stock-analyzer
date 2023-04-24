package stock.me.config

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import stock.me.symbols.populate.consumer.FinnHubConsumer
import stock.me.symbols.populate.consumer.StockConsumer
import stock.me.symbols.populate.service.DefaultSymbolPopulatorService
import stock.me.symbols.populate.service.SymbolPopulatorService
import stock.me.symbols.search.route.DefaultSymbolSearchProvider
import stock.me.symbols.search.route.SymbolSearchProvider
import stock.me.symbols.search.service.DefaultSymbolSearchService
import stock.me.symbols.search.service.SymbolSearchService
import stock.me.symbols.trending.service.DefaultTrendingSymbolsService
import stock.me.symbols.trending.service.TrendingSymbolsService

/**
 * Make components available with dependency injection using Kodein
 */
val kodein = DI {

    bindSingleton { getCacheManager() }

    bindSingleton { getRedisClient() }

    bindSingleton<StockConsumer> { FinnHubConsumer() }

    bindSingleton<SymbolPopulatorService> { DefaultSymbolPopulatorService(instance(), instance()) }

    bindSingleton<TrendingSymbolsService> { DefaultTrendingSymbolsService(instance()) }

    bindSingleton<SymbolSearchService> { DefaultSymbolSearchService(instance(), instance()) }

    bindSingleton<SymbolSearchProvider> { DefaultSymbolSearchProvider(instance()) }
}