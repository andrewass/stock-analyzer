package stock.me.service.symbolpopulator

import kotlinx.coroutines.delay
import org.slf4j.LoggerFactory
import redis.clients.jedis.JedisPooled
import stock.me.consumer.StockConsumer
import stock.me.model.Stock

class EntityPopulatorServiceRedis(
    private val jedis: JedisPooled,
    private val stockConsumer: StockConsumer
) : SymbolPopulatorService {

    private val logger = LoggerFactory.getLogger(EntityPopulatorServiceRedis::class.simpleName)

    override suspend fun populateStocksByTickerSymbol() {
        logger.info("Populating stocks by ticker symbol : Started")
        val exchanges = getStockExchanges()
        logger.info("Fetched ${exchanges.size} exchanges")

        exchanges.forEach { exchange ->
            stockConsumer.getAllStocksFromExchange(exchange).also { stocks ->
                logger.info("Fetched ${stocks.size} stocks from exchange $exchange")
                stocks.forEach { insertSymbol(it)}
            }
            delay(60000L)
        }
        logger.info("Populating stocks by ticker symbol : Completed")
        delay(60000L)
    }

    private fun insertSymbol(stock: Stock) {
        stock.symbol.allPrefixes().forEach {
            jedis.zadd("symbols",0.00, it)
        }
        jedis.zadd("symbols",0.00,stock.symbol+"*")
        jedis.zadd(stock.symbol, 0.00, stock.description)

        jedis.hset("description", stock.symbol, stock.description)
    }

    private fun getStockExchanges(): List<String> {
        return this::class.java.getResourceAsStream("/domain/stockexchanges.csv")
            .bufferedReader()
            .readLines()
            .map { it.split(",") }
            .map { it[0] }
    }

    private fun String.allPrefixes() : List<String> {
        val prefixes = mutableListOf<String>()
        for (i in 1..this.length){
            prefixes.add(this.substring(0,i))
        }
        return prefixes
    }
}