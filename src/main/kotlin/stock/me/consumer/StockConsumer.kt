package stock.me.consumer

import stock.me.model.Exchange

interface StockConsumer {

    suspend fun getAllStockExchanges(): List<Exchange>

    fun getAllSymbols()

}