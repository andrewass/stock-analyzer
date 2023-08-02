package stock.me.symbols.search.consumer

import stock.me.symbols.domain.HistoricalPrice
import stock.me.symbols.domain.SymbolFinancials
import stock.me.symbols.search.domain.CurrentPriceResponse
import stock.me.symbols.search.domain.Period

interface SymbolConsumer {
    suspend fun getCurrentPriceSymbol(symbol: String): CurrentPriceResponse

    suspend fun getCurrentPriceSymbols(symbols: List<String>): List<CurrentPriceResponse>

    suspend fun getFinancialsSymbol(symbol: String): SymbolFinancials

    suspend fun getHistoricalPriceSymbol(symbol: String, period: Period): List<HistoricalPrice>
}