package stock.me.symbols.search

import kotlinx.serialization.Serializable

@Serializable
data class SymbolSuggestion(
    val symbol: String,
    val description: String
)