package stock.me.service

import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.transactions.transaction
import stock.me.consumer.StockConsumer
import stock.me.model.Exchange
import stock.me.model.ExchangeEntity
import stock.me.service.mapper.toExchangeEntity

class DefaultTaskService : TaskService {

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

    private fun getStoredExchanges() = transaction {
        ExchangeEntity.all()
    }

    private fun notAlreadyStored(exchange: Exchange, storedExchanges: SizedIterable<ExchangeEntity>) = transaction {
        storedExchanges.none { exchange.name == it.fullName }
    }

}