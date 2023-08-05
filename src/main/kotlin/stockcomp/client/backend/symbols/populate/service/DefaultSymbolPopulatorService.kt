package stockcomp.client.backend.symbols.populate.service

import kotlinx.coroutines.delay
import org.slf4j.LoggerFactory
import redis.clients.jedis.JedisPooled
import stockcomp.client.backend.symbols.domain.Stock
import stockcomp.client.backend.symbols.populate.consumer.SymbolPopulatorConsumer
import java.util.*

class DefaultSymbolPopulatorService(
    private val jedis: JedisPooled,
    private val symbolPopulatorConsumer: SymbolPopulatorConsumer
) : SymbolPopulatorService {

    private val logger = LoggerFactory.getLogger(DefaultSymbolPopulatorService::class.simpleName)

    override suspend fun populateStocksByTickerSymbol() {
        logger.info("Populating stocks by ticker symbol : Started")
        val exchanges = getStockExchanges()
        logger.info("Fetched ${exchanges.size} exchanges")

        exchanges
            .filter { it == "US" }
            .forEach { exchange ->
            symbolPopulatorConsumer.getAllSymbolsFromExchange(exchange)
                .also { stocks ->
                    logger.info("Fetched ${stocks.size} stocks from exchange $exchange")
                    stocks.forEach { insertSymbol(it) }
                }
            delay(60000L)
        }
        logger.info("Populating stocks by ticker symbol : Completed")
        delay(60000L)
    }

    private fun insertSymbol(stock: Stock) {
        val symbol = stock.symbol.lowercase(Locale.getDefault())
        symbol.allPrefixes().forEach {
            jedis.zadd("symbols", 0.00, it)
        }
        jedis.zadd("symbols", 0.00, "$symbol*")
        jedis.hset("description", symbol, stock.description)
    }

    private fun getStockExchanges(): List<String> {
        return this::class.java.getResourceAsStream("/domain/stockexchanges.csv")
            .bufferedReader()
            .readLines()
            .map { it.split(",") }
            .map { it[0] }
    }

    private fun String.allPrefixes(): List<String> {
        val prefixes = mutableListOf<String>()
        for (i in 1..this.length) {
            prefixes.add(this.substring(0, i))
        }
        return prefixes
    }
}