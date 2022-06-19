package stock.me.symbols.domain

import kotlinx.serialization.Serializable

@Serializable
data class Stock(
    val symbol: String,
    val description: String,
    val stockQuote: StockQuote? = null,
    val stockStats: StockStats? = null
)