package stockcomp.client.backend.symbols.populate.consumer

import stockcomp.client.backend.symbols.domain.Stock

interface SymbolPopulatorConsumer {
    suspend fun getAllSymbolsFromExchange(exchange: String): List<Stock>
}