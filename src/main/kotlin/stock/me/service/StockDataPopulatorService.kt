package stock.me.service

import stock.me.consumer.StockConsumer

interface StockDataPopulatorService {

    fun getHistoricalDividends(stockConsumer: StockConsumer)
}