package stockcomp.client.backend.symbols.domain

import kotlinx.serialization.Serializable

@Serializable
data class CurrentPrice(
    val symbol: String,
    val companyName: String,
    val currentPrice: Double,
    val previousClose: Double,
    val currency: String,
    val usdPrice: Double,
    val priceChange: Double,
    val percentageChange: Double,

)