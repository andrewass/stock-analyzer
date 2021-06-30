package stock.me.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable



object Stocks : LongIdTable() {
    val ticker = varchar("ticker", 10)
    val name = varchar("full_name", 250)
    val exchange = varchar("exchange", 100)
}


class StockEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<StockEntity>(Stocks)

    var ticker by Stocks.ticker
    var name by Stocks.name
    var exchange by Stocks.exchange

    fun toStock() = Stock(ticker, name, exchange)
}


@Serializable
data class Stock(
    val ticker: String,
    val name: String,
    @SerialName("primary_exchange")
    val exchange: String,
)