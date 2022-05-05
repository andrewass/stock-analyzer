package stock.me.symbols.model


data class StockQuote(
    val symbol : String,
    val name : String,
    val price: Double,
    val openPrice: Double,
    val previousClose: Double,
    val dayLow: Double,
    val dayHigh: Double,
    val usdPrice: Double,
    val currency : String
)