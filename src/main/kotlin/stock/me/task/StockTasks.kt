package stock.me.task

import io.ktor.application.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.instance
import org.kodein.di.ktor.di
import stock.me.consumer.StockConsumer
import stock.me.service.TaskService


fun Application.initStockTasks() {
    val stockConsumer by di().instance<StockConsumer>()
    val taskService by di().instance<TaskService>()

    GlobalScope.launch {
        populateStockExchanges(stockConsumer, taskService)
    }
}


private suspend fun populateStockExchanges(stockConsumer: StockConsumer, taskService: TaskService) {
    taskService.populateStockExchanges(stockConsumer)
}