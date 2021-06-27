package stock.me.task

import io.ktor.application.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.kodein.di.instance
import org.kodein.di.ktor.di
import org.slf4j.LoggerFactory
import stock.me.consumer.StockConsumer
import stock.me.service.TaskService


const val EXCHANGE_DELAY = "ktor.task.exchangeDelay"

fun Application.initStockTasks() {
    val stockConsumer by di().instance<StockConsumer>()
    val taskService by di().instance<TaskService>()
    val exchangeDelay = environment.config.property(EXCHANGE_DELAY).getString().toLong()

    GlobalScope.launch {
        while (true) {
            LoggerFactory.getLogger(Application::class.simpleName).info("Populating exchanges")
            taskService.populateStockExchanges(stockConsumer)
            delay(exchangeDelay)
        }
    }
}
