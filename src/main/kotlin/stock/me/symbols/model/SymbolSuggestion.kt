package stock.me.symbols.model

import kotlinx.serialization.Serializable

@Serializable
data class SymbolSuggestion(
    val symbol: String,
    val description: String
)