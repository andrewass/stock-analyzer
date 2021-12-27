package stock.me.provider

import kotlinx.serialization.json.JsonElement
import stock.me.provider.response.HistoricalQuoteDto
import stock.me.provider.response.StockQuoteDto

interface ServiceProvider {

    fun getSymbolSuggestions(query: String): List<JsonElement>

    fun getStockQuote(symbol: String): StockQuoteDto

    fun getHistoricalQuotes(symbol: String): List<HistoricalQuoteDto>

    fun getStockQuotesOfTrendingSymbols(): List<StockQuoteDto>

}
