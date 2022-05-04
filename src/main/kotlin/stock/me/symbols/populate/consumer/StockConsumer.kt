package stock.me.symbols.populate.consumer

import stock.me.symbols.model.Stock

interface StockConsumer {

    suspend fun getAllStocksFromExchange(exchange : String): List<Stock>
}