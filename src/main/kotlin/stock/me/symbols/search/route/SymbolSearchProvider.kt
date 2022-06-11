package stock.me.symbols.search.route

import stock.me.symbols.domain.HistoricalQuote
import stock.me.symbols.domain.Stock
import stock.me.symbols.domain.StockQuote
import stock.me.symbols.domain.SymbolSuggestion

interface SymbolSearchProvider {

    fun getSymbolSuggestions(query: String): List<SymbolSuggestion>

    fun getStockSymbolInformation(symbol: String): Stock

    fun getStockQuote(symbol: String): StockQuote

    fun getHistoricalQuotes(symbol: String): List<HistoricalQuote>

    fun getStockQuotesOfTrendingSymbols(): List<StockQuote>
}