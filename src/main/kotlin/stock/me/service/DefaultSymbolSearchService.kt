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
import org.kodein.di.instance
import stock.me.config.kodein
import stock.me.model.Currency
import stock.me.service.cache.addHistoricalQuotesCache
import stock.me.service.cache.getHistoricalQuotesCache
import stock.me.service.mapper.toHistoricalPriceDto
import stock.me.service.mapper.toStockQuoteDto
import stock.me.service.response.HistoricalQuoteDto
import stock.me.service.response.StockQuoteDto
import yahoofinance.Stock
import yahoofinance.YahooFinance
import yahoofinance.histquotes.Interval
import java.util.*
import java.util.stream.Collectors.toList

class DefaultSymbolSearchService : SymbolSearchService {

    private val restClient by kodein.instance<RestHighLevelClient>()

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

    override fun getStockQuote(symbol: String): StockQuoteDto =
        YahooFinance.get(symbol)
            ?.let { mapToStockQuote(it) }
            ?: throw NotFoundException("Stock Quote : No results found for $symbol")


    override fun getHistoricalQuotes(symbol: String): List<HistoricalQuoteDto> {
        val (from, to) = getFromAndToDates()

        val historicalPrices = getHistoricalQuotesCache(symbol)
            ?: YahooFinance.get(symbol, from, to, Interval.DAILY)
                ?.also { addHistoricalQuotesCache(symbol, it) }?.history
            ?: throw NotFoundException("No historical prices found for $symbol")

        return historicalPrices.stream()
            .filter { it.close != null }
            .map { toHistoricalPriceDto(it) }
            .collect(toList())
    }

    override fun getStockQuotesOfTrendingSymbols(): List<StockQuoteDto> =
        YahooFinance.get(getTrendingSymbols()).values
            .map { mapToStockQuote(it) }

    private fun mapToStockQuote(stock: Stock): StockQuoteDto {
        val currency = Currency.valueOf(stock.currency)
        val usdPrice = getUsdPrice(stock.quote.price.toDouble(), currency)

        return toStockQuoteDto(stock, currency, usdPrice)
    }

    private fun getTrendingSymbols(): Array<String> =
        arrayOf("AAPL", "GOOGL", "BABA", "AMZN", "MSFT", "NVDA", "FB", "TSLA", "V", "PLTR")


    private fun getUsdPrice(price: Double, currency: Currency): Double {
        return if (currency == Currency.USD) {
            price
        } else {
            val fxQuote = YahooFinance.getFx(currency.forexCode).price.toDouble()
            price / fxQuote
        }
    }

    private fun getFromAndToDates(): Pair<Calendar, Calendar> {
        val from = Calendar.getInstance()
        val to = Calendar.getInstance()
        from.add(Calendar.YEAR, -10)

        return Pair(from, to)
    }

    private fun createBoolQuery(query: String) =
        BoolQueryBuilder()
            .should(
                QueryBuilders.matchPhrasePrefixQuery("symbol", query)
            )
            .should(
                QueryBuilders.matchPhrasePrefixQuery("description", query)
            )
}
