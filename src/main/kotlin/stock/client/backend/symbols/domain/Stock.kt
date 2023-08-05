package stock.client.backend.symbols.domain

import kotlinx.serialization.Serializable

@Serializable
data class Stock(
    val symbol: String,
    val description: String,
)