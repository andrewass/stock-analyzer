package stock.me.service

import kotlinx.serialization.json.JsonElement
import yahoofinance.Stock

interface SymbolService {

    fun getSymbolSuggestions(query: String): List<JsonElement>

    fun getStockSymbolInformation(symbol: String)

    fun getStockQuote(symbol: String): Stock

    fun getHistoricalQuotes(symbol: String): Stock

    fun getStockQuotesOfTrendingSymbols(): Collection<Stock>
}