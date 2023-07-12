package stock.me.symbols.search.route

import stock.me.symbols.domain.*

interface SymbolSearchProvider {

    fun getSymbolSuggestions(query: String): List<SymbolSuggestion>

    fun getStockDetails(symbol: String): Stock

    fun getHistoricalQuotes(symbol: String): List<HistoricalQuote>
}