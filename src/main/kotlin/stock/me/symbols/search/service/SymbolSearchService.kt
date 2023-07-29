package stock.me.symbols.search.service

import stock.me.symbols.domain.CurrentPrice
import stock.me.symbols.domain.HistoricalPriceResponse
import stock.me.symbols.domain.SymbolFinancials
import stock.me.symbols.domain.SymbolSuggestion

interface SymbolSearchService {

    fun getSymbolSuggestions(query: String): List<SymbolSuggestion>

    suspend fun getStockSymbolFinancials(symbol: String): SymbolFinancials

    suspend fun getHistoricalPrice(symbol: String): HistoricalPriceResponse

    suspend fun getCurrentPriceOfSymbol(symbol: String): CurrentPrice

    suspend fun getCurrentPriceOfTrendingSymbols(): List<CurrentPrice>
}