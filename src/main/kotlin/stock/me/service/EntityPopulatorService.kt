package stock.me.service

import stock.me.consumer.StockConsumer

interface EntityPopulatorService {

    suspend fun getStockExchanges() : List<String>

    suspend fun populateStocksByTickerSymbol(stockConsumer: StockConsumer)
}