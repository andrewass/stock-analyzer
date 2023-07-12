package stock.me.symbols.search.route

import stock.me.symbols.domain.*
import stock.me.symbols.search.service.SymbolSearchService

class DefaultSymbolSearchProvider(
    private val symbolService: SymbolSearchService
) : SymbolSearchProvider {

    override fun getSymbolSuggestions(query: String): List<SymbolSuggestion> =
        symbolService.getSymbolSuggestions(query)

    override fun getStockDetails(symbol: String): Stock =
        toStockDetails(symbolService.getStockDetails(symbol))

    override fun getHistoricalQuotes(symbol: String): List<HistoricalQuote> =
        toHistoricalQuotes(symbolService.getHistoricalQuotes(symbol))
}