package stock.client.backend.symbols.search.consumer

import stock.client.backend.symbols.domain.HistoricalPrice
import stock.client.backend.symbols.domain.SymbolFinancials
import stock.client.backend.symbols.search.domain.CurrentPriceResponse
import stock.client.backend.symbols.search.domain.Period

interface SymbolSearchConsumer {
    suspend fun getCurrentPriceSymbol(symbol: String): CurrentPriceResponse

    suspend fun getCurrentPriceSymbols(symbols: List<String>): List<CurrentPriceResponse>

    suspend fun getFinancialsSymbol(symbol: String): SymbolFinancials

    suspend fun getHistoricalPriceSymbol(symbol: String, period: Period): List<HistoricalPrice>
}