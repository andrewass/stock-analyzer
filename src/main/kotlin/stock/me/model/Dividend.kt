package stock.me.model

import kotlinx.datetime.serializers.LocalDateIso8601Serializer
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.`java-time`.date

object Dividends : LongIdTable() {
    val ticker = varchar("ticker", 10)
    val paymentDate = date("payment_date")
    val amount = double("amount")
    val stockFinancial = reference("stock_financial",StockFinancials)
}

class DividendEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<DividendEntity>(Dividends)

    var ticker by Dividends.ticker
    var paymentDate by Dividends.paymentDate
    var amount by Dividends.amount
    var stockFinancial by StockFinancialEntity referencedOn Dividends.stockFinancial

    fun toDividend() = Dividend(ticker, paymentDate.toKotlinLocalDate(), amount)
}

@Serializable
data class Dividend(
    val ticker: String,
    @Serializable(with = LocalDateIso8601Serializer::class)
    val paymentDate: kotlinx.datetime.LocalDate,
    val amount: Double
)