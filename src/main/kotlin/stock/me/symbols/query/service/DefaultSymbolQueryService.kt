package stock.me.symbols.query.service

import io.ktor.features.*
import redis.clients.jedis.JedisPooled
import stock.me.symbols.query.SymbolSuggestion
import yahoofinance.Stock
import yahoofinance.YahooFinance
import yahoofinance.histquotes.Interval
import java.util.*

class DefaultSymbolQueryService(
    private val jedis: JedisPooled
) : SymbolQueryService {

    override fun getSymbolSuggestions(query: String): List<SymbolSuggestion> {
        val rank = jedis.zrank("symbols", query.lowercase(Locale.getDefault()))

        return jedis.zrange("symbols", rank + 1, rank + 101)
            .filter { it.endsWith('*') }
            .take(10)
            .map { it.removeSuffix("*") }
            .map { toSymbolSuggestion(it) }
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

    private fun toSymbolSuggestion(symbol: String) =
        SymbolSuggestion(
            symbol = symbol,
            description = jedis.hget("description", symbol)
        )

    private fun getTrendingSymbols(): Array<String> =
        arrayOf("AAPL", "GOOGL", "BABA", "AMZN", "MSFT", "NVDA", "FB", "TSLA", "V", "PLTR")

    private fun getFromAndToDates(): Pair<Calendar, Calendar> {
        val from = Calendar.getInstance()
        val to = Calendar.getInstance()
        from.add(Calendar.YEAR, -10)

        return Pair(from, to)
    }
}