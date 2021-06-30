package stock.me.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.`java-time`.date
import java.time.LocalDate

object Dividends : LongIdTable() {
    val ticker = varchar("ticker", 10)
    val paymentDate = date("payment_date")
    val amount = double("amount")
}


class DividendEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<DividendEntity>(Dividends)

    var ticker by Dividends.ticker
    var paymentDate by Dividends.paymentDate
    var amount by Dividends.amount

    fun toDividend() = Dividend(ticker, paymentDate, amount)
}


@Serializable
data class Dividend(
    val ticker: String,
    @Contextual
    val paymentDate: LocalDate,
    val amount : Double
)