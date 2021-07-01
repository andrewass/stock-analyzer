package stock.me.service

import stock.me.consumer.StockConsumer

interface StockDataPopulatorService {

    suspend fun getHistoricalDividends(stockConsumer: StockConsumer)
}