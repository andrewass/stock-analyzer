package stockcomp.client.backend.symbols.search.service

import redis.clients.jedis.JedisPooled
import stockcomp.client.backend.symbols.domain.CurrentPrice
import stockcomp.client.backend.symbols.domain.HistoricalPriceResponse
import stockcomp.client.backend.symbols.domain.Stock
import stockcomp.client.backend.symbols.domain.SymbolFinancials
import stockcomp.client.backend.symbols.search.consumer.SymbolSearchConsumer
import stockcomp.client.backend.symbols.search.domain.CurrentPriceResponse
import stockcomp.client.backend.symbols.search.domain.Period
import stockcomp.client.backend.symbols.trending.service.TrendingSymbolsService
import java.util.Locale

interface SymbolSearchService {
    fun getSymbolSuggestions(query: String): List<Stock>

    suspend fun getStockSymbolFinancials(symbol: String): SymbolFinancials

    suspend fun getHistoricalPrice(
        symbol: String,
        period: Period,
    ): HistoricalPriceResponse

    suspend fun getCurrentPriceOfSymbol(symbol: String): CurrentPrice

    suspend fun getCurrentPriceOfTrendingSymbols(): List<CurrentPrice>
}

class DefaultSymbolSearchService(
    private val jedis: JedisPooled,
    private val trendingSymbolsService: TrendingSymbolsService,
    private val symbolConsumer: SymbolSearchConsumer,
) : SymbolSearchService {
    override fun getSymbolSuggestions(query: String): List<Stock> {
        val rank: Long? = jedis.zrank("symbols", query.lowercase(Locale.getDefault()))
        if (rank != null) {
            return jedis
                .zrange("symbols", rank + 1, rank + 101)
                .filter { it.endsWith('*') }
                .take(10)
                .map { it.removeSuffix("*") }
                .map { Stock(symbol = it, description = jedis.hget("description", it)) }
        }
        return emptyList()
    }

    override suspend fun getStockSymbolFinancials(symbol: String): SymbolFinancials = symbolConsumer.getFinancialsSymbol(symbol)

    override suspend fun getHistoricalPrice(
        symbol: String,
        period: Period,
    ): HistoricalPriceResponse =
        getHistoricalQuotesCache(CacheKey(symbol, period))
            ?: symbolConsumer
                .getHistoricalPriceSymbol(symbol, period)
                .also { addHistoricalQuotesCache(CacheKey(symbol, period), it) }

    override suspend fun getCurrentPriceOfSymbol(symbol: String): CurrentPrice =
        toCurrentPrice(symbolConsumer.getCurrentPriceSymbol(symbol))

    override suspend fun getCurrentPriceOfTrendingSymbols(): List<CurrentPrice> =
        symbolConsumer
            .getCurrentPriceSymbols(trendingSymbolsService.getTrendingSymbols(10))
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
            usdPrice = source.currentPrice,
        )
}
