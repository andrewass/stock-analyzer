package stock.me.symbols.model

import kotlinx.datetime.LocalDate


class HistoricalQuote(
    val price : Double,
    val date : LocalDate,
    val volume : Long
)