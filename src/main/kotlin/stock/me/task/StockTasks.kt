package stock.me.task

import io.ktor.application.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.launch
import org.elasticsearch.client.RestHighLevelClient
import org.kodein.di.instance
import org.kodein.di.ktor.di
import stock.me.consumer.StockConsumer
import stock.me.service.EntityPopulatorService

suspend fun Application.initStockTasks() {
    val stockConsumer by di().instance<StockConsumer>()
    val restClient by di().instance<RestHighLevelClient>()
    val entityPopulatorService by di().instance<EntityPopulatorService>()

    CoroutineScope(Default).launch {
        while (true) {
            entityPopulatorService.populateStocksByTickerSymbol(stockConsumer, restClient)
        }
    }
}