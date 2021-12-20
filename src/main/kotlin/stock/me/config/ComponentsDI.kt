package stock.me.config

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import stock.me.consumer.FinnHubConsumer
import stock.me.consumer.StockConsumer
import stock.me.service.DefaultEntityPopulatorService
import stock.me.service.DefaultSymbolSearchService
import stock.me.service.EntityPopulatorService
import stock.me.service.SymbolSearchService

/**
 * Make components available with dependency injection using Kodein
 */
val kodein =  DI {

    bindSingleton<StockConsumer> { FinnHubConsumer() }

    bindSingleton<EntityPopulatorService> { DefaultEntityPopulatorService() }

    bindSingleton<SymbolSearchService> { DefaultSymbolSearchService() }

    bindSingleton { getRestHighLevelClient() }

    bindSingleton { getElasticSearchClient() }

    bindSingleton { getCacheManager() }
}