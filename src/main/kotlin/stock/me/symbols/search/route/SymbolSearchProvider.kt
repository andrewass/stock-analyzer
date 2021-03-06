package stock.me.symbols.search.route

import stock.me.symbols.domain.HistoricalQuote
import stock.me.symbols.domain.Stock
import stock.me.symbols.domain.SymbolSuggestion

interface SymbolSearchProvider {

    fun getSymbolSuggestions(query: String): List<SymbolSuggestion>

    fun getStockDetails(symbol: String): Stock

    fun getStockQuote(symbol: String): Stock

    fun getHistoricalQuotes(symbol: String): List<HistoricalQuote>

    fun getStockQuotesOfTrendingSymbols(): List<Stock>
}