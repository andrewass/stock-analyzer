package stock.me.service.symbolquery

import kotlinx.serialization.Serializable

@Serializable
data class SymbolSuggestion(
    val symbol: String,
    val description: String
)
