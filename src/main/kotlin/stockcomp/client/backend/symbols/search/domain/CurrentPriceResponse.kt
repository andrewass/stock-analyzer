package stockcomp.client.backend.symbols.search.domain

import kotlinx.serialization.Serializable

@Serializable
data class CurrentPriceResponse(
    val symbol: String,
    val companyName: String,
    val currentPrice: Double,
    val previousClose: Double,
    val currency: String,
)
