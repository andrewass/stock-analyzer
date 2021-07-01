package stock.me.service

import kotlinx.coroutines.delay
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import stock.me.consumer.StockConsumer
import stock.me.model.*
import stock.me.service.mapper.toExchangeEntity
import stock.me.service.mapper.toStockEntity
import stock.me.service.mapper.toStockFinancialEntity

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
            stocks.filter { stockNotPreviouslyPersisted(it) }
                .forEach { persistStock(it) }
            currentPage = nextPage
            isRemainingQueries = nextPage != null
            delay(15000L)
        }
        logger.info("Populating stocks by ticker symbol : Completed")
    }

    private fun stockNotPreviouslyPersisted(stock: Stock): Boolean = transaction {
        StockEntity.find { Stocks.ticker eq stock.ticker }.none()
    }

    private fun persistStock(stock: Stock) = transaction {
        toStockEntity(stock)
        toStockFinancialEntity(StockFinancial(stock.ticker))
    }

    private fun getStoredExchanges() = transaction {
        ExchangeEntity.all()
    }

    private fun notAlreadyStored(exchange: Exchange, storedExchanges: SizedIterable<ExchangeEntity>) = transaction {
        storedExchanges.none { exchange.name == it.fullName }
    }
}