package stock.me.model

import kotlinx.serialization.Serializable


@Serializable
data class Exchange(
    val symbol: String,
    val fullName: String
)