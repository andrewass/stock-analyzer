package stock.me.task

import co.elastic.clients.elasticsearch.ElasticsearchClient
import io.ktor.application.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.launch
import org.elasticsearch.client.RestHighLevelClient
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import stock.me.consumer.StockConsumer
import stock.me.service.EntityPopulatorService

suspend fun Application.initStockTasks() {
    val stockConsumer by closestDI().instance<StockConsumer>()
    val restClient by closestDI().instance<RestHighLevelClient>()
    val esClient by closestDI().instance<ElasticsearchClient>()
    val entityPopulatorService by closestDI().instance<EntityPopulatorService>()

    CoroutineScope(Default).launch {
        while (true) {
            entityPopulatorService.populateStocksByTickerSymbol(stockConsumer, restClient)
        }
    }
}
