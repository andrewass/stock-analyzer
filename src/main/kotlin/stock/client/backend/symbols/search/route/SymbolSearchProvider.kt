package stock.client.backend.symbols.search.route

import stock.client.backend.symbols.domain.*

interface SymbolSearchProvider {

    fun getSymbolSuggestions(query: String): List<SymbolSuggestion>
}