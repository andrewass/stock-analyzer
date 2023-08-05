package stock.client.backend.symbols.search.route

import stock.client.backend.symbols.domain.*
import stock.client.backend.symbols.search.service.SymbolSearchService

class DefaultSymbolSearchProvider(
    private val symbolService: SymbolSearchService
) : SymbolSearchProvider {

    override fun getSymbolSuggestions(query: String): List<Stock> =
        symbolService.getSymbolSuggestions(query)
}