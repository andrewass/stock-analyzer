package stock.me.service

import io.ktor.features.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.index.query.BoolQueryBuilder
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.index.query.RegexpFlag
import org.elasticsearch.search.builder.SearchSourceBuilder
import stock.me.service.mapper.toStockQuoteDto
import stock.me.service.response.StockQuoteDto
import yahoofinance.YahooFinance

class DefaultSymbolSearchService : SymbolSearchService {

    override fun getSymbolSuggestions(restClient: RestHighLevelClient, query: String): List<JsonElement> {
        val request = SearchRequest("stocks")
        val ssb = SearchSourceBuilder()
        ssb.query(createBoolQuery(query))
        request.source(ssb)
        val response = restClient.search(request, RequestOptions.DEFAULT)

        return response.hits.hits
            .map { it.sourceAsString }
            .map { Json.parseToJsonElement(it) }
    }

    override fun getRealTimePrice(symbol: String): StockQuoteDto {
        val responseQuote = YahooFinance.get(symbol)?.quote
            ?: throw NotFoundException("No stock quote found for $symbol")
        return toStockQuoteDto(responseQuote)
    }

    private fun createBoolQuery(query: String) =
        BoolQueryBuilder()
            .should(
                QueryBuilders.regexpQuery("symbol", ".*$query.*")
                    .caseInsensitive(true)
                    .flags(RegexpFlag.ALL)
            )
            .should(
                QueryBuilders.regexpQuery("description", ".*$query.*")
                    .caseInsensitive(true)
                    .flags(RegexpFlag.ALL)
            )
}