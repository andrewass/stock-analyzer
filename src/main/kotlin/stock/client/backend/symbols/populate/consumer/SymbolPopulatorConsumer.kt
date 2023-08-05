package stock.client.backend.symbols.populate.consumer

import stock.client.backend.symbols.domain.Stock

interface SymbolPopulatorConsumer {
    suspend fun getAllSymbolsFromExchange(exchange: String): List<Stock>
}