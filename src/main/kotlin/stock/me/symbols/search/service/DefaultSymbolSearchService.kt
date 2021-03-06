package stock.me.symbols.search.service

import io.ktor.server.plugins.*
import redis.clients.jedis.JedisPooled
import stock.me.symbols.domain.SymbolSuggestion
import yahoofinance.Stock
import yahoofinance.YahooFinance
import yahoofinance.histquotes.Interval
import java.util.*

class DefaultSymbolSearchService(
    private val jedis: JedisPooled
) : SymbolSearchService {

    override fun getSymbolSuggestions(query: String): List<SymbolSuggestion> {
        val rank : Long? = jedis.zrank("symbols", query.lowercase(Locale.getDefault()))
        if (rank != null) {
            return jedis.zrange("symbols", rank + 1, rank + 101)
                .filter { it.endsWith('*') }
                .take(10)
                .map { it.removeSuffix("*") }
                .map { toSymbolSuggestion(it) }
        }
        return emptyList()
    }

    override fun getStockDetails(symbol: String): Stock =
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
        arrayOf("AAPL", "GOOGL", "BABA", "AMZN", "MSFT", "NVDA", "AMD", "TSLA", "V", "PLTR")

    private fun getFromAndToDates(): Pair<Calendar, Calendar> {
        val from = Calendar.getInstance()
        val to = Calendar.getInstance()
        from.add(Calendar.YEAR, -10)

        return Pair(from, to)
    }
}