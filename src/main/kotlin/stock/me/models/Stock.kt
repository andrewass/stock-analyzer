package stock.me.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

val stockStorage = mutableListOf(
    Stock("AAPL", "Apple Inc", "Technology"),
    Stock("FB", "Facebook", "Technology")
)

/**
 * Represents a table in the database
 */
object Stocks : LongIdTable() {
    val symbol = varchar("symbol", 10)
    val fullName = varchar("full_name", 250)
    val industry = varchar("industry", 100)
}

/**
 * Represents a row in the table
 */
class StockEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<StockEntity>(Stocks)

    var symbol by Stocks.symbol
    var fullName by Stocks.fullName
    var industry by Stocks.industry

    fun toStock() = Stock(symbol, fullName, industry)
}

@Serializable
data class Stock(
    val symbol: String,
    val fullName: String,
    val industry: String
)