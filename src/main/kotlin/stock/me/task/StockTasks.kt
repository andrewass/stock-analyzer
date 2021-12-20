package stock.me.task

import co.elastic.clients.elasticsearch.ElasticsearchClient
import io.ktor.application.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.launch
import org.elasticsearch.client.RestHighLevelClient
import org.kodein.di.instance
import stock.me.config.kodein
import stock.me.consumer.StockConsumer
import stock.me.service.EntityPopulatorService

suspend fun Application.initStockTasks() {
    val stockConsumer by kodein.instance<StockConsumer>()
    val restClient by kodein.instance<RestHighLevelClient>()
    val esClient by kodein.instance<ElasticsearchClient>()
    val entityPopulatorService by kodein.instance<EntityPopulatorService>()

    CoroutineScope(Default).launch {
        while (true) {
            entityPopulatorService.populateStocksByTickerSymbol(stockConsumer, restClient)
        }
    }
}
