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
import stock.me.model.Currency
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
        val stock = YahooFinance.get(symbol) ?: throw NotFoundException("Stock Quote : No results found for $symbol")
        val quote = stock.quote
        val currency = Currency.valueOf(stock.currency)
        val usdPrice = getUsdPrice(quote.price.toDouble(), currency)

        return toStockQuoteDto(quote, currency, usdPrice)
    }

    override fun getStockStats(symbol: String): StockStatsDto {
        val stockStats = YahooFinance.get(symbol)?.stats
            ?: throw NotFoundException("No stock stats found for $symbol")

        return toStockStatsDto(stockStats)
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
