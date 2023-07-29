package stock.me.symbols.search.service

import stock.me.symbols.domain.CurrentPrice
import stock.me.symbols.domain.SymbolFinancials
import stock.me.symbols.domain.SymbolSuggestion
import yahoofinance.Stock

interface SymbolSearchService {

    fun getSymbolSuggestions(query: String): List<SymbolSuggestion>

    suspend fun getStockSymbolFinancials(symbol: String): SymbolFinancials

    fun getHistoricalQuotes(symbol: String): Stock

    suspend fun getCurrentPriceOfSymbol(symbol: String): CurrentPrice

    suspend fun getCurrentPriceOfTrendingSymbols(): List<CurrentPrice>
}