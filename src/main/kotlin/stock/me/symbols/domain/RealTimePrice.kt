package stock.me.symbols.domain

import kotlinx.serialization.Serializable

@Serializable
data class RealTimePrice(
    val price: Double,
    val usdPrice: Double,
    val currency : Currency
)
