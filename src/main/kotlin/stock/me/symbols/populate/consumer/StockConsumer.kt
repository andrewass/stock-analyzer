package stock.me.symbols.populate.consumer

import stock.me.symbols.domain.Stock

interface StockConsumer {

    suspend fun getAllStocksFromExchange(exchange : String): List<Stock>
}