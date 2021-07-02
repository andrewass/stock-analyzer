package stock.me.service

import stock.me.consumer.StockConsumer

interface StockDataPopulatorService {

    suspend fun populateHistoricalDividends(stockConsumer: StockConsumer)

    suspend fun populateHistoricalFinancials(stockConsumer: StockConsumer)
}