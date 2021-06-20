package stock.me.models

import kotlinx.serialization.Serializable

@Serializable
data class Stock(
    val symbol: String
)