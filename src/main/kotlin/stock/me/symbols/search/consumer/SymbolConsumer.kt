package stock.me.symbols.search.consumer

import stock.me.symbols.domain.SymbolFinancials
import stock.me.symbols.search.consumer.request.CurrentPriceResponse

interface SymbolConsumer {
    suspend fun getCurrentPriceSymbol(symbol: String) : CurrentPriceResponse

    suspend fun getCurrentPriceSymbols(symbols: List<String>) : List<CurrentPriceResponse>

    suspend fun getFinancialsSymbol(symbol: String): SymbolFinancials
}