package stock.me.symbols.trending.service

interface TrendingSymbolsService {

    fun updateWithQueriedSymbol(symbol: String)

    fun getTrendingSymbols(symbolCount: Long): List<String>

    fun getTrendingSymbolsFallback(): List<String>
}