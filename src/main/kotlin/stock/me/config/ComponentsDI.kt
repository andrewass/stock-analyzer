package stock.me.config

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import stock.me.symbols.populate.consumer.FinnHubConsumer
import stock.me.symbols.populate.consumer.StockConsumer
import stock.me.symbols.search.provider.DefaultServiceProvider
import stock.me.symbols.search.provider.ServiceProvider
import stock.me.symbols.populate.service.SymbolPopulatorService
import stock.me.symbols.populate.service.SymbolPopulatorServiceRedis
import stock.me.symbols.search.service.SymbolQueryService
import stock.me.symbols.search.service.DefaultSymbolQueryService

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

    bindSingleton<SymbolQueryService> { DefaultSymbolQueryService(instance()) }

    bindSingleton<ServiceProvider> { DefaultServiceProvider(instance()) }
}