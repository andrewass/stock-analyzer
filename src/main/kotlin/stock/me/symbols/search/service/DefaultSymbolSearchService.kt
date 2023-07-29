package stock.me.symbols.search.service

import io.ktor.server.plugins.*
import redis.clients.jedis.JedisPooled
import stock.me.symbols.domain.CurrentPrice
import stock.me.symbols.domain.SymbolSuggestion
import stock.me.symbols.search.consumer.SymbolConsumer
import stock.me.symbols.search.consumer.request.CurrentPriceResponse
import stock.me.symbols.trending.service.TrendingSymbolsService
import yahoofinance.Stock
import yahoofinance.YahooFinance
import yahoofinance.histquotes.Interval
import java.util.*

class DefaultSymbolSearchService(
    private val jedis: JedisPooled,
    private val trendingSymbolsService: TrendingSymbolsService,
    private val symbolConsumer: SymbolConsumer
) : SymbolSearchService {

    override fun getSymbolSuggestions(query: String): List<SymbolSuggestion> {
        val rank: Long? = jedis.zrank("symbols", query.lowercase(Locale.getDefault()))
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
            ?.also { trendingSymbolsService.updateWithQueriedSymbol(symbol) }
            ?: throw NotFoundException("Stock symbol information : No results found for $symbol")


    override fun getHistoricalQuotes(symbol: String): Stock {
        val (from, to) = getFromAndToDates()
        return getHistoricalQuotesCache(symbol)
            ?: YahooFinance.get(symbol, from, to, Interval.DAILY)
                ?.also { addHistoricalQuotesCache(symbol, it) }
            ?: throw NotFoundException("No historical prices found for $symbol")
    }

    override suspend fun getCurrentPriceOfSymbol(symbol: String): CurrentPrice =
        toCurrentPrice(symbolConsumer.getCurrentPriceSymbol(symbol))


    override suspend fun getCurrentPriceOfTrendingSymbols(): List<CurrentPrice> =
        symbolConsumer.getCurrentPriceSymbols(trendingSymbolsService.getTrendingSymbols(10))
            .map { toCurrentPrice(it) }


    private fun toCurrentPrice(source: CurrentPriceResponse): CurrentPrice =
        CurrentPrice(
            symbol = source.symbol,
            companyName = source.companyName,
            currency = source.currency,
            currentPrice = source.currentPrice,
            previousClose = source.previousClose,
            priceChange = source.currentPrice - source.previousClose,
            percentageChange = ((source.currentPrice - source.previousClose) / source.previousClose) * 100,
            usdPrice = source.currentPrice
        )


    private fun toSymbolSuggestion(symbol: String) =
        SymbolSuggestion(
            symbol = symbol,
            description = jedis.hget("description", symbol)
        )

    private fun getFromAndToDates(): Pair<Calendar, Calendar> {
        val from = Calendar.getInstance()
        val to = Calendar.getInstance()
        from.add(Calendar.YEAR, -10)

        return Pair(from, to)
    }
}