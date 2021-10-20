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
import stock.me.model.Currency
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

    override fun getSymbolSuggestions(restClient: RestHighLevelClient, query: String): List<JsonElement> {
        val request = SearchRequest("stocks")
        val ssb = SearchSourceBuilder()
        ssb.query(createBoolQuery(query))
        ssb.sort(FieldSortBuilder("symbol.keyword").order(SortOrder.ASC))
        request.source(ssb)
        val response = restClient.search(request, RequestOptions.DEFAULT)

        return response.hits.hits
            .map { it.sourceAsString }
            .map { Json.parseToJsonElement(it) }
    }

    override fun getStockQuote(symbol: String): StockQuoteDto {
        val stock = YahooFinance.get(symbol) ?: throw NotFoundException("Stock Quote : No results found for $symbol")

        return mapToStockQuote(stock)
    }

    override fun getHistoricalQuotes(symbol: String): List<HistoricalQuoteDto> {
        val (from, to) = getFromAndToDates()
        val historicalPrices = YahooFinance.get(symbol, from, to, Interval.DAILY)?.history
            ?: throw NotFoundException("No historical prices found for $symbol")

        return historicalPrices.stream()
            .filter { it.close != null }
            .map { toHistoricalPriceDto(it) }
            .collect(toList())
    }

    override fun getStockQuotesOfTrendingSymbols(): List<StockQuoteDto> {
        val symbols = getTrendingSymbols()

        return YahooFinance.get(symbols).values
            .map { mapToStockQuote(it) }
    }

    private fun mapToStockQuote(stock: Stock): StockQuoteDto {
        val currency = Currency.valueOf(stock.currency)
        val usdPrice = getUsdPrice(stock.quote.price.toDouble(), currency)

        return toStockQuoteDto(stock, currency, usdPrice)
    }

    //TODO:replace stubbed method
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
