package stock.me.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable


/**
 * Represents a table in the database
 */
object Stocks : LongIdTable() {
    val ticker = varchar("ticker", 10)
    val name = varchar("full_name", 250)
    val market = varchar("market", 100)
}

/**
 * Represents a row in the table
 */
class StockEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<StockEntity>(Stocks)

    var ticker by Stocks.ticker
    var name by Stocks.name
    var market by Stocks.market

    fun toStock() = Stock(ticker, name, market)
}

@Serializable
data class Stock(
    val ticker: String,
    val name: String,
    val market: String
)