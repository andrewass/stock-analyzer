package stock.me.task

import io.ktor.application.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.launch
import org.kodein.di.instance
import org.kodein.di.ktor.di
import stock.me.consumer.StockConsumer
import stock.me.service.EntityPopulatorService
import stock.me.service.StockDataPopulatorService

suspend fun Application.initStockTasks() {
    val stockConsumer by di().instance<StockConsumer>()
    val entityPopulatorService by di().instance<EntityPopulatorService>()
    val stockDataPopulatorService by di().instance<StockDataPopulatorService>()


    CoroutineScope(Default).launch {
        while (true) {
            entityPopulatorService.populateStockExchanges(stockConsumer)
            entityPopulatorService.populateStocksByTickerSymbol(stockConsumer)
        }
    }

    CoroutineScope(Default).launch {
        while (true) {
            stockDataPopulatorService.getHistoricalDividends(stockConsumer)
        }
    }
}
