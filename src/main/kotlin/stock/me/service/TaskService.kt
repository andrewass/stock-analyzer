package stock.me.service

import stock.me.consumer.StockConsumer

interface TaskService {

    suspend fun populateStockExchanges(stockConsumer: StockConsumer)
}