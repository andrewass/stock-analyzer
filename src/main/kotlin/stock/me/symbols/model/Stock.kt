package stock.me.symbols.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Stock(
    val symbol: String,
    val description: String,
    val stockQuote: StockQuote?,
    val stockStats: StockStats?
)