package stock.me.service

import stock.me.consumer.StockConsumer

class DefaultTaskService() : TaskService {

    override suspend fun populateStockExchanges(stockConsumer: StockConsumer) {
        val response = stockConsumer.getAllStockExchanges()
    }
}