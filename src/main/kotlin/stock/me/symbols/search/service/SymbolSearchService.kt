package stock.me.symbols.search.service

import stock.me.symbols.domain.CurrentPrice
import stock.me.symbols.domain.HistoricalPriceResponse
import stock.me.symbols.domain.SymbolFinancials
import stock.me.symbols.domain.SymbolSuggestion
import stock.me.symbols.search.domain.Period

interface SymbolSearchService {

    fun getSymbolSuggestions(query: String): List<SymbolSuggestion>

    suspend fun getStockSymbolFinancials(symbol: String): SymbolFinancials

    suspend fun getHistoricalPrice(symbol: String, period: Period): HistoricalPriceResponse

    suspend fun getCurrentPriceOfSymbol(symbol: String): CurrentPrice

    suspend fun getCurrentPriceOfTrendingSymbols(): List<CurrentPrice>
}