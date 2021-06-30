package stock.me.task

import io.ktor.application.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.kodein.di.instance
import org.kodein.di.ktor.di
import stock.me.consumer.StockConsumer
import stock.me.service.EntityPopulatorService

const val EXCHANGE_DELAY = "ktor.task.exchangeDelay"

suspend fun Application.initStockTasks() {
    val stockConsumer by di().instance<StockConsumer>()
    val entityPopulatorService by di().instance<EntityPopulatorService>()

    coroutineScope {
        launch {
            while (true) {
                entityPopulatorService.populateStockExchanges(stockConsumer)
                entityPopulatorService.populateStocksByTickerSymbol(stockConsumer)
            }
        }
    }
}
