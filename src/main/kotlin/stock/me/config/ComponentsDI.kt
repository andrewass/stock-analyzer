package stock.me.config

import org.apache.http.HttpHost
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton
import stock.me.consumer.FinnHubConsumer
import stock.me.consumer.StockConsumer
import stock.me.service.*

/**
 * Make components available with dependency injection by using Kodein
 */
fun DI.MainBuilder.bindComponents() {

    bind<StockConsumer>() with singleton { FinnHubConsumer() }

    bind<EntityPopulatorService>() with singleton { DefaultEntityPopulatorService() }

    bind<RestHighLevelClient>() with singleton { RestHighLevelClient(
        RestClient.builder(HttpHost("elastic-search", 9200, "http"))
    ) }

}