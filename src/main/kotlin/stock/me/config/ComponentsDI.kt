package stock.me.config

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import stock.me.symbols.populator.consumer.FinnHubConsumer
import stock.me.symbols.populator.consumer.StockConsumer
import stock.me.provider.DefaultServiceProvider
import stock.me.provider.ServiceProvider
import stock.me.symbols.populator.service.SymbolPopulatorService
import stock.me.symbols.populator.service.SymbolPopulatorServiceRedis
import stock.me.symbols.query.service.SymbolQueryService
import stock.me.symbols.query.service.DefaultSymbolQueryService

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