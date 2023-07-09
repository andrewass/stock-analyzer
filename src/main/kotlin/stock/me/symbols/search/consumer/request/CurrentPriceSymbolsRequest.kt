package stock.me.symbols.search.consumer.request

import kotlinx.serialization.Serializable

@Serializable
data class CurrentPriceSymbolsRequest(
    val symbols: List<String>
)
