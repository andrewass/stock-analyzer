package stock.me.service

import stock.me.consumer.StockConsumer

interface EntityPopulatorService {

    suspend fun populateStockExchanges(stockConsumer: StockConsumer)

    suspend fun populateStocksByTickerSymbol(stockConsumer: StockConsumer)
}