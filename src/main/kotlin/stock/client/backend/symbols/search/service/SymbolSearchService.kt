package stock.client.backend.symbols.search.service

import stock.client.backend.symbols.domain.*
import stock.client.backend.symbols.search.domain.Period

interface SymbolSearchService {

    fun getSymbolSuggestions(query: String): List<Stock>

    suspend fun getStockSymbolFinancials(symbol: String): SymbolFinancials

    suspend fun getHistoricalPrice(symbol: String, period: Period): HistoricalPriceResponse

    suspend fun getCurrentPriceOfSymbol(symbol: String): CurrentPrice

    suspend fun getCurrentPriceOfTrendingSymbols(): List<CurrentPrice>
}