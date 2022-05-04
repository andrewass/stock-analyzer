package stock.me.symbols.search.service

import stock.me.symbols.search.SymbolSuggestion
import yahoofinance.Stock

interface SymbolQueryService {

    fun getSymbolSuggestions(query: String): List<SymbolSuggestion>

    fun getStockSymbolInformation(symbol: String): Stock

    fun getStockQuote(symbol: String): Stock

    fun getHistoricalQuotes(symbol: String): Stock

    fun getStockQuotesOfTrendingSymbols(): Collection<Stock>
}