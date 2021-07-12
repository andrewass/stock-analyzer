package stock.me.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable


object Stocks : LongIdTable() {
    val symbol = varchar("ticker", 10)
    val description = varchar("full_name", 250)
}

class StockEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<StockEntity>(Stocks)

    var symbol by Stocks.symbol
    var description by Stocks.description
}

@Serializable
data class Stock(
    val symbol: String,
    val description: String,
)