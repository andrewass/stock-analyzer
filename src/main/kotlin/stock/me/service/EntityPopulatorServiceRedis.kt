package stock.me.service

import kotlinx.coroutines.delay
import org.slf4j.LoggerFactory
import redis.clients.jedis.JedisPooled
import stock.me.consumer.StockConsumer
import stock.me.model.Stock

class EntityPopulatorServiceRedis(
    private val jedis: JedisPooled,
    private val stockConsumer: StockConsumer
) : EntityPopulatorService {

    private val logger = LoggerFactory.getLogger(EntityPopulatorServiceRedis::class.simpleName)

    override suspend fun populateStocksByTickerSymbol() {
        logger.info("Populating stocks by ticker symbol : Started")
        val exchanges = getStockExchanges()
        logger.info("Fetched ${exchanges.size} exchanges")

        exchanges.forEach { exchange ->
            stockConsumer.getAllStocksFromExchange(exchange).also { stocks ->
                logger.info("Fetched ${stocks.size} stocks from exchange $exchange")
                stocks.forEach { insertStock(it)}
            }
            delay(60000L)
        }
        logger.info("Populating stocks by ticker symbol : Completed")
        delay(60000L)
    }

    private fun insertStock(stock: Stock) {
        jedis.zadd(stock.symbol, 0.00, stock.description)
    }

    private fun getStockExchanges(): List<String> {
        return this::class.java.getResourceAsStream("/domain/stockexchanges.csv")
            .bufferedReader()
            .readLines()
            .map { it.split(",") }
            .map { it[0] }
    }

}