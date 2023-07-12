package stock.me.symbols.domain

import kotlinx.serialization.Serializable

@Serializable
data class CurrentPrice(
    val symbol: String,
    val price: Double
)
