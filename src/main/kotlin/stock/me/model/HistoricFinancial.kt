package stock.me.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.serializers.LocalDateIso8601Serializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.`java-time`.date

object HistoricFinancials : LongIdTable() {
    val ticker = varchar("ticker", 10)
    val calendarDate = date("calendar_date")
    val priceToSalesRatio = double("price_to_sales").nullable()
    val priceToEarningsRatio = double("price_to_earnings").nullable()
    val priceToBookValue = double("price_to_book").nullable()
    val dividendsPerShare = double("dividends_per_share").nullable()
    val stockFinancial = reference("stock_financial", StockFinancials)
}

class HistoricFinancialEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<HistoricFinancialEntity>(HistoricFinancials)

    var ticker by HistoricFinancials.ticker
    var calendarDate by HistoricFinancials.calendarDate
    var priceToSalesRatio by HistoricFinancials.priceToSalesRatio
    var priceToEarningsRatio by HistoricFinancials.priceToEarningsRatio
    var priceToBookValue by HistoricFinancials.priceToBookValue
    var dividendsPerShare by HistoricFinancials.dividendsPerShare
    var stockFinancial by StockFinancialEntity referencedOn HistoricFinancials.stockFinancial
}

@Serializable
data class HistoricFinancial(
    val ticker: String,
    @Serializable(with = LocalDateIso8601Serializer::class)
    val calendarDate: LocalDate,
    val priceToSalesRatio: Double? = null,
    val priceToEarningsRatio: Double? = null,
    val priceToBookValue: Double? = null,
    @SerialName("dividendsPerBasicCommonShare")
    val dividendsPerShare: Double? = null
)