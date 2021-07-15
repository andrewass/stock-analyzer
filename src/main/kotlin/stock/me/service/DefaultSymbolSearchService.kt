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
import stock.me.service.mapper.toHistoricalPriceDto
import stock.me.service.mapper.toStockQuoteDto
import stock.me.service.mapper.toStockStatsDto
import stock.me.service.response.HistoricalQuoteDto
import stock.me.service.response.StockQuoteDto
import stock.me.service.response.StockStatsDto
import yahoofinance.YahooFinance
import yahoofinance.histquotes.Interval
import java.util.*
import java.util.stream.Collectors.toList

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

    override fun getStockQuote(symbol: String): StockQuoteDto {
        val responseQuote = YahooFinance.get(symbol)?.quote
            ?: throw NotFoundException("No stock quote found for $symbol")

        return toStockQuoteDto(responseQuote)
    }

    override fun getStockStats(symbol: String): StockStatsDto {
        val stockStats = YahooFinance.get(symbol)?.stats
            ?: throw NotFoundException("No stock stats found for $symbol")

        return toStockStatsDto(stockStats)
    }

    override fun getHistoricalQuotes(symbol: String): List<HistoricalQuoteDto> {
        val from = Calendar.getInstance()
        val to = Calendar.getInstance()
        from.add(Calendar.YEAR, -10)

        val historicPrices = YahooFinance.get(symbol, from, to, Interval.DAILY)?.history
            ?: throw NotFoundException("No historical prices for $symbol")

        return historicPrices.stream()
            .filter { it.close != null }
            .map { toHistoricalPriceDto(it) }
            .collect(toList())
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