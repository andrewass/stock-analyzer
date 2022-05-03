package stock.me.config

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import stock.me.consumer.FinnHubConsumer
import stock.me.consumer.StockConsumer
import stock.me.provider.DefaultServiceProvider
import stock.me.provider.ServiceProvider
import stock.me.service.symbolpopulator.SymbolPopulatorService
import stock.me.service.symbolpopulator.EntityPopulatorServiceRedis
import stock.me.service.symbolquery.SymbolQueryService
import stock.me.service.symbolquery.DefaultSymbolQueryService

/**
 * Make components available with dependency injection using Kodein
 */
val kodein = DI {

    bindSingleton { getCacheManager() }

    bindSingleton { getRedisClient() }

    bindSingleton<StockConsumer> { FinnHubConsumer() }

    bindSingleton<SymbolPopulatorService> {
        EntityPopulatorServiceRedis(instance(), instance())
    }

    bindSingleton<SymbolQueryService> { DefaultSymbolQueryService(instance()) }

    bindSingleton<ServiceProvider> { DefaultServiceProvider(instance()) }
}