package stock.client.backend.symbols.search.service

import redis.clients.jedis.JedisPooled
import stock.client.backend.symbols.domain.*
import stock.client.backend.symbols.search.consumer.SymbolSearchConsumer
import stock.client.backend.symbols.search.domain.CurrentPriceResponse
import stock.client.backend.symbols.search.domain.Period
import stock.client.backend.symbols.trending.service.TrendingSymbolsService
import java.util.*

class DefaultSymbolSearchService(
    private val jedis: JedisPooled,
    private val trendingSymbolsService: TrendingSymbolsService,
    private val symbolConsumer: SymbolSearchConsumer
) : SymbolSearchService {

    override fun getSymbolSuggestions(query: String): List<Stock> {
        val rank: Long? = jedis.zrank("symbols", query.lowercase(Locale.getDefault()))
        if (rank != null) {
            return jedis.zrange("symbols", rank + 1, rank + 101)
                .filter { it.endsWith('*') }
                .take(10)
                .map { it.removeSuffix("*") }
                .map { Stock(symbol = it, description = jedis.hget("description", it)) }
        }
        return emptyList()
    }

    override suspend fun getStockSymbolFinancials(symbol: String): SymbolFinancials =
        symbolConsumer.getFinancialsSymbol(symbol)


    override suspend fun getHistoricalPrice(symbol: String, period: Period): HistoricalPriceResponse {
        return getHistoricalQuotesCache(CacheKey(symbol, period))
            ?: HistoricalPriceResponse(historicalPriceList = symbolConsumer.getHistoricalPriceSymbol(symbol, period))
                .also { addHistoricalQuotesCache(CacheKey(symbol, period), it) }
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
}