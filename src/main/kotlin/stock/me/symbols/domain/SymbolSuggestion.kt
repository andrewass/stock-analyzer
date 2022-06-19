package stock.me.symbols.domain

import kotlinx.serialization.Serializable

@Serializable
data class SymbolSuggestion(
    val symbol: String,
    val description: String
)