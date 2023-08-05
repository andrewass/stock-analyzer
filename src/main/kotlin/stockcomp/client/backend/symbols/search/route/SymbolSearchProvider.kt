package stockcomp.client.backend.symbols.search.route

import stockcomp.client.backend.symbols.domain.*

interface SymbolSearchProvider {

    fun getSymbolSuggestions(query: String): List<Stock>
}