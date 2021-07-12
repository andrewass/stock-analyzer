package stock.me.service

import kotlinx.coroutines.delay
import org.slf4j.LoggerFactory
import stock.me.consumer.StockConsumer

class DefaultEntityPopulatorService : EntityPopulatorService {

    private val logger = LoggerFactory.getLogger(DefaultEntityPopulatorService::class.simpleName)

    override suspend fun populateStocksByTickerSymbol(stockConsumer: StockConsumer) {
        logger.info("Populating stocks by ticker symbol : Started")
        val exchanges = getStockExchanges()
        logger.info("Fetched ${exchanges.size} exchanges")
        logger.info("Populating stocks by ticker symbol : Completed")
        delay(30000L)
    }

    override suspend fun getStockExchanges(): List<String> {
        return this::class.java.getResourceAsStream("/domain/stockexchanges.csv")
            .bufferedReader()
            .readLines()
            .map { it.split(",") }
            .map { it[0] }
    }
}
