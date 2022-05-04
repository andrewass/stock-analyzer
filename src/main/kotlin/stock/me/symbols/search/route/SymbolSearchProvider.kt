package stock.me.symbols.search.route

import stock.me.symbols.model.HistoricalQuote
import stock.me.symbols.model.Stock
import stock.me.symbols.model.StockQuote
import stock.me.symbols.model.SymbolSuggestion

interface SymbolSearchProvider {

    fun getSymbolSuggestions(query: String): List<SymbolSuggestion>

    fun getStockSymbolInformation(symbol: String): Stock

    fun getStockQuote(symbol: String): StockQuote

    fun getHistoricalQuotes(symbol: String): List<HistoricalQuote>

    fun getStockQuotesOfTrendingSymbols(): List<StockQuote>
}