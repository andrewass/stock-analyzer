package stock.me.symbols.query.service

import stock.me.symbols.query.SymbolSuggestion
import yahoofinance.Stock

interface SymbolQueryService {

    fun getSymbolSuggestions(query: String): List<SymbolSuggestion>

    fun getStockSymbolInformation(symbol: String): Stock

    fun getStockQuote(symbol: String): Stock

    fun getHistoricalQuotes(symbol: String): Stock

    fun getStockQuotesOfTrendingSymbols(): Collection<Stock>
}