package stock.me.service

import org.jetbrains.exposed.sql.transactions.transaction
import stock.me.model.Stock
import stock.me.model.StockEntity
import stock.me.model.Stocks


class StockService {

    fun getAllStocks(): List<Stock> = transaction {
        StockEntity.all().map(StockEntity::toStock)
    }

    fun findBySymbol(symbol: String) = transaction {
        StockEntity.find { Stocks.ticker eq symbol }
            .map { StockEntity::toStock }
    }

    fun addStock(stock: Stock) = transaction {
        StockEntity.new {
            this.ticker = stock.ticker
            this.name = stock.name
            this.exchange = stock.exchange
        }
    }
}