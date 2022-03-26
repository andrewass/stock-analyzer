package stock.me.service

import io.ktor.features.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.index.query.BoolQueryBuilder
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.elasticsearch.search.sort.FieldSortBuilder
import org.elasticsearch.search.sort.SortOrder
import stock.me.service.cache.addHistoricalQuotesCache
import stock.me.service.cache.getHistoricalQuotesCache
import yahoofinance.Stock
import yahoofinance.YahooFinance
import yahoofinance.histquotes.Interval
import java.util.*

class DefaultSymbolService(
    private val restClient: RestHighLevelClient
) : SymbolService {

    override fun getSymbolSuggestions(query: String): List<JsonElement> {
        val response = SearchSourceBuilder().apply {
            query(createBoolQuery(query))
            sort(FieldSortBuilder("symbol.keyword").order(SortOrder.ASC))
        }
            .let { SearchRequest("stocks").source(it) }
            .let { restClient.search(it, RequestOptions.DEFAULT) }

        return response.hits.hits
            .map { it.sourceAsString }
            .map { Json.parseToJsonElement(it) }
    }

    override fun getStockSymbolInformation(symbol: String): Stock =
        YahooFinance.get(symbol)
            ?: throw NotFoundException("Stock symbol information : No results found for $symbol")


    override fun getStockQuote(symbol: String): Stock =
        YahooFinance.get(symbol)
            ?: throw NotFoundException("Stock Quote : No results found for $symbol")


    override fun getHistoricalQuotes(symbol: String): Stock {
        val (from, to) = getFromAndToDates()

        return getHistoricalQuotesCache(symbol)
            ?: YahooFinance.get(symbol, from, to, Interval.DAILY)
                ?.also { addHistoricalQuotesCache(symbol, it) }
            ?: throw NotFoundException("No historical prices found for $symbol")
    }

    override fun getStockQuotesOfTrendingSymbols(): Collection<Stock> =
        YahooFinance.get(getTrendingSymbols()).values

    private fun getTrendingSymbols(): Array<String> =
        arrayOf("AAPL", "GOOGL", "BABA", "AMZN", "MSFT", "NVDA", "FB", "TSLA", "V", "PLTR")

    private fun getFromAndToDates(): Pair<Calendar, Calendar> {
        val from = Calendar.getInstance()
        val to = Calendar.getInstance()
        from.add(Calendar.YEAR, -10)

        return Pair(from, to)
    }

    private fun createBoolQuery(query: String) =
        BoolQueryBuilder()
            .should(QueryBuilders.matchPhrasePrefixQuery("symbol", query))
            .should(QueryBuilders.matchPhrasePrefixQuery("description", query))
}
