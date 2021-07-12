package stock.me.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable


object Exchanges : LongIdTable() {
    val symbol = varchar("symbol", 100)
    val fullName = varchar("full_name", 200)
}

class ExchangeEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<ExchangeEntity>(Exchanges)

    var symbol by Exchanges.symbol
    var fullName by Exchanges.fullName

    fun toExchange() = Exchange(symbol, fullName)
}

@Serializable
data class Exchange(
    val symbol: String,
    val fullName: String
)