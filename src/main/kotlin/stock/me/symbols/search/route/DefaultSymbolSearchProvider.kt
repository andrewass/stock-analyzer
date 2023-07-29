package stock.me.symbols.search.route

import stock.me.symbols.domain.*
import stock.me.symbols.search.service.SymbolSearchService

class DefaultSymbolSearchProvider(
    private val symbolService: SymbolSearchService
) : SymbolSearchProvider {

    override fun getSymbolSuggestions(query: String): List<SymbolSuggestion> =
        symbolService.getSymbolSuggestions(query)
}