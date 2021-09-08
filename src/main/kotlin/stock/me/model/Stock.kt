package stock.me.model

import kotlinx.serialization.Serializable


@Serializable
data class Stock(
    val symbol: String,
    val description: String,
)