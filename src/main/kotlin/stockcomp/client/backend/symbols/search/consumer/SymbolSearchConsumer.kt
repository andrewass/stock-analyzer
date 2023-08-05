package stockcomp.client.backend.symbols.search.consumer

import stockcomp.client.backend.symbols.domain.HistoricalPrice
import stockcomp.client.backend.symbols.domain.SymbolFinancials
import stockcomp.client.backend.symbols.search.domain.CurrentPriceResponse
import stockcomp.client.backend.symbols.search.domain.Period

interface SymbolSearchConsumer {
    suspend fun getCurrentPriceSymbol(symbol: String): CurrentPriceResponse

    suspend fun getCurrentPriceSymbols(symbols: List<String>): List<CurrentPriceResponse>

    suspend fun getFinancialsSymbol(symbol: String): SymbolFinancials

    suspend fun getHistoricalPriceSymbol(symbol: String, period: Period): List<HistoricalPrice>
}