package stockcomp.client.backend.symbols.search.domain

import kotlinx.serialization.Serializable

@Serializable
data class CurrentPriceSymbolsRequest(
    val symbols: List<String>,
)
