package stock.me.symbols.search.service

import stock.me.symbols.domain.SymbolSuggestion
import yahoofinance.Stock

interface SymbolSearchService {

    fun getSymbolSuggestions(query: String): List<SymbolSuggestion>

    fun getStockDetails(symbol: String): Stock

    fun getHistoricalQuotes(symbol: String): Stock

    fun getStockQuotesOfTrendingSymbols(): Collection<Stock>
}