package stock.me.symbols.search.route

import stock.me.symbols.domain.HistoricalQuote
import stock.me.symbols.domain.RealTimePrice
import stock.me.symbols.domain.Stock
import stock.me.symbols.domain.SymbolSuggestion
import stock.me.symbols.search.service.SymbolSearchService

class DefaultSymbolSearchProvider(
    private val symbolService: SymbolSearchService
) : SymbolSearchProvider {

    override fun getSymbolSuggestions(query: String): List<SymbolSuggestion> =
        symbolService.getSymbolSuggestions(query)

    override fun getRealTimePrice(symbol: String): RealTimePrice =
        toRealTimePrice(symbolService.getStockDetails(symbol))

    override fun getStockDetails(symbol: String): Stock =
        toStockDetails(symbolService.getStockDetails(symbol))

    override fun getHistoricalQuotes(symbol: String): List<HistoricalQuote> =
        toHistoricalQuotes(symbolService.getHistoricalQuotes(symbol))

    override fun getStockQuotesOfTrendingSymbols(): List<Stock> =
        symbolService.getStockQuotesOfTrendingSymbols()
            .map { toStockSimple(it) }
}