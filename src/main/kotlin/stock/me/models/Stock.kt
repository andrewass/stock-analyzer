package stock.me.models

import kotlinx.serialization.Serializable

val stockStorage = mutableListOf(Stock("AAPL", "Apple Inc", "Technology"),
    Stock("FB", "Facebook", "Technology"))

@Serializable
data class Stock(
    val symbol: String,
    val fullName: String,
    val industry: String
)