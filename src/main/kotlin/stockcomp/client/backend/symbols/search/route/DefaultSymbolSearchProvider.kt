package stockcomp.client.backend.symbols.search.route

import stockcomp.client.backend.symbols.domain.*
import stockcomp.client.backend.symbols.search.service.SymbolSearchService

class DefaultSymbolSearchProvider(
    private val symbolService: SymbolSearchService
) : SymbolSearchProvider {

    override fun getSymbolSuggestions(query: String): List<Stock> =
        symbolService.getSymbolSuggestions(query)
}