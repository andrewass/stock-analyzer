package stock.me.config

import org.apache.http.HttpHost
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton
import stock.me.consumer.FinnHubConsumer
import stock.me.consumer.StockConsumer
import stock.me.service.DefaultEntityPopulatorService
import stock.me.service.DefaultSymbolSearchService
import stock.me.service.EntityPopulatorService
import stock.me.service.SymbolSearchService

/**
 * Make components available with dependency injection by using Kodein
 */
fun DI.MainBuilder.bindComponents() {

    bind<StockConsumer>() with singleton { FinnHubConsumer() }

    bind<EntityPopulatorService>() with singleton { DefaultEntityPopulatorService() }

    bind<SymbolSearchService>() with singleton { DefaultSymbolSearchService() }

    bind<RestHighLevelClient>() with singleton {
        RestHighLevelClient(
            RestClient.builder(
                HttpHost(
                    System.getenv("ELASTIC_SEARCH"),
                    System.getenv("ELASTIC_SEARCH_PORT").toInt(),
                    "http"
                )
            )
        )
    }
}