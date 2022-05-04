package stock.me.symbols.model

import kotlinx.serialization.Serializable


@Serializable
data class Stock(
    val symbol: String,
    val description: String,
    val stockQuote: StockQuote?,
    val stockStats: StockStats?
)