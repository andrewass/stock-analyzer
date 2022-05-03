package stock.me.symbols.query

import kotlinx.serialization.Serializable

@Serializable
data class SymbolSuggestion(
    val symbol: String,
    val description: String
)