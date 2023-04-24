package stock.me.symbols.trending.service

interface TrendingSymbolsService {

    fun updateWithQueriedSymbol(symbol: String)

    fun getTrendingSymbols(symbolCount: Long): Array<String>

    fun getTrendingSymbolsFallback(): Array<String>
}