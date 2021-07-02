package stock.me.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable


object Exchanges : LongIdTable() {
    val market = varchar("market", 100)
    val type = varchar("exchange_type", 100)
    val fullName = varchar("full_name", 200)
    val idCode = varchar("id_code", 10).nullable()
}

class ExchangeEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<ExchangeEntity>(Exchanges)

    var market by Exchanges.market
    var type by Exchanges.type
    var fullName by Exchanges.fullName
    var idCode by Exchanges.idCode

    fun toExchange() = Exchange(market, type, fullName, idCode)
}

@Serializable
data class Exchange(
    val market : String,
    val type : String,
    val name : String ,
    val mic : String? = null
)