package stock.me.service.symbolquery

import io.ktor.features.*
import kotlinx.serialization.json.JsonElement
import redis.clients.jedis.JedisPooled
import stock.me.service.cache.addHistoricalQuotesCache
import stock.me.service.cache.getHistoricalQuotesCache
import yahoofinance.Stock
import yahoofinance.YahooFinance
import yahoofinance.histquotes.Interval
import java.util.*

class SymbolServiceRedis(
    private val jedis : JedisPooled
) : SymbolQueryService {

    override fun getSymbolSuggestions(query: String): List<JsonElement> {
        TODO("Not yet implemented")
    }

    override fun getStockSymbolInformation(symbol: String): Stock {
        TODO("Not yet implemented")
    }

    override fun getStockQuote(symbol: String): Stock {
        TODO("Not yet implemented")
    }

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
}