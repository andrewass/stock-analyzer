package stock.me.symbols.search.consumer

import stock.me.symbols.domain.CurrentPrice

interface SymbolConsumer {

    suspend fun getCurrentPriceSymbol(symbol: String) : CurrentPrice

    suspend fun getCurrentPriceSymbols(symbols: List<String>) : List<CurrentPrice>
}