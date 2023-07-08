package stock.me.symbols.search.consumer

interface SymbolConsumer {

    fun getCurrentPriceSymbol(symbol: String)

    suspend fun getCurrentPriceSymbols(symbols: List<String>)
}