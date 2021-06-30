package stock.me.task

import io.ktor.application.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.kodein.di.instance
import org.kodein.di.ktor.di
import stock.me.consumer.StockConsumer
import stock.me.service.EntityPopulatorService
import stock.me.service.StockDataPopulatorService

const val EXCHANGE_DELAY = "ktor.task.exchangeDelay"

suspend fun Application.initStockTasks() {
    val stockConsumer by di().instance<StockConsumer>()
    val entityPopulatorService by di().instance<EntityPopulatorService>()
    val stockDataPopulatorService by di().instance<StockDataPopulatorService>()


    coroutineScope {
        launch {
            while (true) {
                entityPopulatorService.populateStockExchanges(stockConsumer)
                entityPopulatorService.populateStocksByTickerSymbol(stockConsumer)
            }
        }
        launch {
            while (true){
                stockDataPopulatorService.getHistoricalDividends(stockConsumer)
            }
        }

    }
}
