package stockcomp.client.backend.symbols.search.service

import stockcomp.client.backend.symbols.domain.*
import stockcomp.client.backend.symbols.search.domain.Period

interface SymbolSearchService {

    fun getSymbolSuggestions(query: String): List<Stock>

    suspend fun getStockSymbolFinancials(symbol: String): SymbolFinancials

    suspend fun getHistoricalPrice(symbol: String, period: Period): HistoricalPriceResponse

    suspend fun getCurrentPriceOfSymbol(symbol: String): CurrentPrice

    suspend fun getCurrentPriceOfTrendingSymbols(): List<CurrentPrice>
}