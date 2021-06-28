package stock.me.task

import io.ktor.application.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.kodein.di.instance
import org.kodein.di.ktor.di
import org.slf4j.LoggerFactory
import stock.me.consumer.StockConsumer
import stock.me.service.EntityPopulatorService


const val EXCHANGE_DELAY = "ktor.task.exchangeDelay"

fun Application.initStockTasks() {
    val stockConsumer by di().instance<StockConsumer>()
    val entityPopulatorService by di().instance<EntityPopulatorService>()
    val exchangeDelay = environment.config.property(EXCHANGE_DELAY).getString().toLong()


    GlobalScope.launch {
        while (true) {
            LoggerFactory.getLogger(Application::class.simpleName).info("Populating exchanges")
            entityPopulatorService.populateStockExchanges(stockConsumer)
            delay(exchangeDelay)
        }
    }

    GlobalScope.launch {
        entityPopulatorService.populateStocksByTickerSymbol(stockConsumer)
    }
}
