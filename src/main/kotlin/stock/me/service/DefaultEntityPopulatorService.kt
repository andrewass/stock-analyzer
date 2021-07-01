package stock.me.service

import kotlinx.coroutines.delay
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import stock.me.consumer.StockConsumer
import stock.me.model.*
import stock.me.service.mapper.toExchangeEntity
import stock.me.service.mapper.toStockEntity

class DefaultEntityPopulatorService : EntityPopulatorService {

    private val logger = LoggerFactory.getLogger(DefaultEntityPopulatorService::class.simpleName)

    override suspend fun populateStockExchanges(stockConsumer: StockConsumer) {
        logger.info("Populating stock exchanges : Started")

        val storedExchanges = getStoredExchanges()
        stockConsumer.getAllStockExchanges()
            .filter { notAlreadyStored(it, storedExchanges) }
            .forEach {
                transaction {
                    toExchangeEntity(it)
                }
            }

        logger.info("Populating stock exchanges : Completed")
    }

    override suspend fun populateStocksByTickerSymbol(stockConsumer: StockConsumer) {
        logger.info("Populating stocks by ticker symbol : Started")

        var isRemainingQueries = true
        var currentPage: String? = null
        while (isRemainingQueries) {
            val (stocks, nextPage) = stockConsumer.getAllStocks(currentPage)
            stocks.forEach { persistStockIfNotExists(it) }
            currentPage = nextPage
            isRemainingQueries = nextPage != null
            delay(15000L)
        }

        logger.info("Populating stocks by ticker symbol : Completed")
    }

    private fun persistStockIfNotExists(stock: Stock) = transaction {
        if (StockEntity.find { Stocks.ticker eq stock.ticker }.none()) {
            toStockEntity(stock)
        }
    }

    private fun getStoredExchanges() = transaction {
        ExchangeEntity.all()
    }

    private fun notAlreadyStored(exchange: Exchange, storedExchanges: SizedIterable<ExchangeEntity>) = transaction {
        storedExchanges.none { exchange.name == it.fullName }
    }
}