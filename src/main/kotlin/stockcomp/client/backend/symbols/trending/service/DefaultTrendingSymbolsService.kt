package stockcomp.client.backend.symbols.trending.service

import redis.clients.jedis.JedisPooled

class DefaultTrendingSymbolsService(
    private val jedis: JedisPooled
) : TrendingSymbolsService {

    private val symbolHits = "symbolHits"

    override fun updateWithQueriedSymbol(symbol: String) {
        jedis.zincrby(symbolHits, 1.0, symbol)
    }

    override fun getTrendingSymbols(symbolCount: Long): List<String> {
        val trendingSymbols = jedis.zrevrange(symbolHits, 0, symbolCount - 1)
        return if (trendingSymbols.size.toLong() == symbolCount) {
            //TODO: trendingSymbols.toTypedArray() - fix bad data quality
            getTrendingSymbolsFallback()
        } else {
            getTrendingSymbolsFallback()
        }
    }

    override fun getTrendingSymbolsFallback(): List<String> =
        listOf("AAPL", "GOOGL", "BABA", "AMZN", "MSFT", "NVDA", "AMD", "TSLA", "V", "PLTR")
}