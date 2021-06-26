package stock.me.service

import org.jetbrains.exposed.sql.transactions.transaction
import stock.me.models.Stock
import stock.me.models.StockEntity
import stock.me.models.Stocks


class StockService {

    fun getAllStocks(): List<Stock> = transaction {
        StockEntity.all().map(StockEntity::toStock)
    }

    fun addStock(stock: Stock) = transaction {
        StockEntity.new {
            this.symbol = stock.symbol
            this.fullName = stock.fullName
            this.industry = stock.industry
        }
    }

    fun deleteStock(symbol: String) = transaction {
        StockEntity.find { Stocks.symbol eq symbol }
            .forEach { it.delete() }
    }
}