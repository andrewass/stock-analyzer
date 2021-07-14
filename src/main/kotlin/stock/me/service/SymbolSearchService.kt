package stock.me.service

import kotlinx.serialization.json.JsonElement
import org.elasticsearch.client.RestHighLevelClient
import stock.me.service.response.StockQuoteDto

interface SymbolSearchService {
    fun getSymbolSuggestions(restClient: RestHighLevelClient, query: String): List<JsonElement>

    fun getRealTimePrice(symbol: String): StockQuoteDto
}