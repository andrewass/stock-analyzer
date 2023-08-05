package stock.client.backend.symbols.populate.consumer

import stock.client.backend.symbols.domain.Stock

interface StockConsumer {

    suspend fun getAllStocksFromExchange(exchange: String): List<Stock>
}