package stock.me.service

import kotlinx.coroutines.delay
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import stock.me.consumer.StockConsumer
import stock.me.model.Dividend
import stock.me.model.StockFinancialEntity
import stock.me.service.mapper.toDividendEntity

class DefaultStockDataPopulatorService : StockDataPopulatorService {

    private val logger = LoggerFactory.getLogger(DefaultStockDataPopulatorService::class.simpleName)

    override suspend fun getHistoricalDividends(stockConsumer: StockConsumer) {
        logger.info("Populating historical dividends  : Started")
        val stocks = transaction { StockFinancialEntity.all().toList() }
        stocks.forEach { stock ->
            stockConsumer.getDividendsForStock(stock.ticker)
                .forEach { dividend -> persistDividend(dividend, stock) }
            delay(15000L)
        }
        delay(15000L)
    }

    private fun persistDividend(dividend: Dividend, stockFinancialEntity: StockFinancialEntity) = transaction {
        toDividendEntity(dividend, stockFinancialEntity)
    }
}