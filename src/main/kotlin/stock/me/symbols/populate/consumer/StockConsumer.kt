package stock.me.symbols.populate.consumer

import stock.me.model.Stock

interface StockConsumer {

    suspend fun getAllStocksFromExchange(exchange : String): List<Stock>
}