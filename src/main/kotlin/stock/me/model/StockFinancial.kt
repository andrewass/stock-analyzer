package stock.me.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object StockFinancials : LongIdTable() {
    val ticker = varchar("ticker", 10)
}

class StockFinancialEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<StockFinancialEntity>(StockFinancials)

    var ticker by StockFinancials.ticker
    val dividends by DividendEntity referrersOn Dividends.stockFinancial
    val historicFinancials by HistoricFinancialEntity referrersOn HistoricFinancials.stockFinancial
}

@Serializable
data class StockFinancial(
    val ticker: String
)