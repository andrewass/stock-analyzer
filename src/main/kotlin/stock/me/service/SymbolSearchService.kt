package stock.me.service

import kotlinx.serialization.json.JsonElement
import org.elasticsearch.client.RestHighLevelClient
import stock.me.service.response.HistoricalQuoteDto
import stock.me.service.response.StockQuoteDto
import stock.me.service.response.StockStatsDto

interface SymbolSearchService {
    fun getSymbolSuggestions(restClient: RestHighLevelClient, query: String): List<JsonElement>

    fun getStockQuote(symbol: String): StockQuoteDto

    fun getStockStats(symbol: String): StockStatsDto

    fun getHistoricalQuotes(symbol: String): List<HistoricalQuoteDto>
}