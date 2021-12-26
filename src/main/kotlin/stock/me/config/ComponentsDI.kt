package stock.me.config

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import stock.me.consumer.FinnHubConsumer
import stock.me.consumer.StockConsumer
import stock.me.graphql.GraphQLDataFetchers
import stock.me.graphql.GraphQLProvider
import stock.me.service.DefaultEntityPopulatorService
import stock.me.service.DefaultSymbolSearchService
import stock.me.service.EntityPopulatorService
import stock.me.service.SymbolSearchService

/**
 * Make components available with dependency injection using Kodein
 */
val kodein = DI {

    bindSingleton { getRestHighLevelClient() }

    bindSingleton { getElasticSearchClient() }

    bindSingleton { getCacheManager() }

    bindSingleton<StockConsumer> { FinnHubConsumer() }

    bindSingleton<EntityPopulatorService> {
        DefaultEntityPopulatorService(instance(), instance(), instance())
    }

    bindSingleton<SymbolSearchService> { DefaultSymbolSearchService(instance(), instance()) }

    bindSingleton { GraphQLDataFetchers() }

    bindSingleton { GraphQLProvider(instance()) }
}