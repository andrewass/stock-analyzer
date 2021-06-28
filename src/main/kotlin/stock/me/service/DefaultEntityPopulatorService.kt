package stock.me.service

import kotlinx.coroutines.delay
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.transactions.transaction
import stock.me.consumer.StockConsumer
import stock.me.model.*
import stock.me.service.mapper.toExchangeEntity

class DefaultEntityPopulatorService : EntityPopulatorService {

    override suspend fun populateStockExchanges(stockConsumer: StockConsumer) {
        val storedExchanges = getStoredExchanges()

        stockConsumer.getAllStockExchanges()
            .filter { notAlreadyStored(it, storedExchanges) }
            .forEach {
                transaction {
                    toExchangeEntity(it)
                }
            }
    }

    override suspend fun populateStocksByTickerSymbol(stockConsumer: StockConsumer) {
        var isRemainingQueries = true
        var currentPage: String? = null
        while (isRemainingQueries) {
            val (stocks, nextPage) = stockConsumer.getAllStocks(currentPage)
            stocks.forEach { persistStockIfNotExists(it) }
            //currentPage = nextPage
            isRemainingQueries = true
            delay(10000L)
        }
    }

    private fun persistStockIfNotExists(stock: Stock) = transaction {
        val persistedStock = StockEntity.find { Stocks.ticker eq stock.ticker }
    }


    private fun getStoredExchanges() = transaction {
        ExchangeEntity.all()
    }

    private fun notAlreadyStored(exchange: Exchange, storedExchanges: SizedIterable<ExchangeEntity>) = transaction {
        storedExchanges.none { exchange.name == it.fullName }
    }
}