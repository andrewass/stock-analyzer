package stock.me.service

import kotlinx.coroutines.delay
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import stock.me.consumer.StockConsumer
import stock.me.model.StockEntity

class DefaultStockDataPopulatorService : StockDataPopulatorService {

    private val logger = LoggerFactory.getLogger(DefaultStockDataPopulatorService::class.simpleName)

    override suspend fun getHistoricalDividends(stockConsumer: StockConsumer) {
        logger.info("Populating historical dividends  : Started")
        val stocks = transaction { StockEntity.all().map(StockEntity::toStock) }
        stocks.forEach {
            val dividends = stockConsumer.getDividendsForStock(it)
            delay(15000L)
        }
        delay(15000L)
    }
}