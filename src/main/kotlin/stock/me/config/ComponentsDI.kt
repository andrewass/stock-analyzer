package stock.me.config

import org.apache.http.HttpHost
import org.apache.http.auth.AuthScope
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.impl.client.BasicCredentialsProvider
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

    val credentialsProvider = BasicCredentialsProvider()
        .also {
            it.setCredentials(
                AuthScope.ANY,
                UsernamePasswordCredentials(System.getenv("ES_USERNAME"), System.getenv("ES_PASSWORD"))
            )
        }

    bind<RestHighLevelClient>() with singleton {
        RestHighLevelClient(
            RestClient.builder(
                HttpHost(
                    System.getenv("ELASTIC_SEARCH"),
                    System.getenv("ELASTIC_SEARCH_PORT").toInt(),
                    "http"
                )
            ).setHttpClientConfigCallback { it.setDefaultCredentialsProvider(credentialsProvider) }
        )
    }
}