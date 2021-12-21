package stock.me.service

import kotlinx.serialization.json.JsonElement
import stock.me.service.response.HistoricalQuoteDto
import stock.me.service.response.StockQuoteDto

interface SymbolSearchService {

    fun getSymbolSuggestions(query: String): List<JsonElement>

    fun getStockQuote(symbol: String): StockQuoteDto

    fun getHistoricalQuotes(symbol: String): List<HistoricalQuoteDto>

    fun getStockQuotesOfTrendingSymbols(): List<StockQuoteDto>
}