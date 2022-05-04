package stock.me.config

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import stock.me.symbols.populate.consumer.FinnHubConsumer
import stock.me.symbols.populate.consumer.StockConsumer
import stock.me.symbols.search.provider.DefaultSymbolSearchProvider
import stock.me.symbols.search.provider.SymbolSearchProvider
import stock.me.symbols.populate.service.SymbolPopulatorService
import stock.me.symbols.populate.service.SymbolPopulatorServiceRedis
import stock.me.symbols.search.service.SymbolSearchService
import stock.me.symbols.search.service.DefaultSymbolSearchService

/**
 * Make components available with dependency injection using Kodein
 */
val kodein = DI {

    bindSingleton { getCacheManager() }

    bindSingleton { getRedisClient() }

    bindSingleton<StockConsumer> { FinnHubConsumer() }

    bindSingleton<SymbolPopulatorService> {
        SymbolPopulatorServiceRedis(instance(), instance())
    }

    bindSingleton<SymbolSearchService> { DefaultSymbolSearchService(instance()) }

    bindSingleton<SymbolSearchProvider> { DefaultSymbolSearchProvider(instance()) }
}