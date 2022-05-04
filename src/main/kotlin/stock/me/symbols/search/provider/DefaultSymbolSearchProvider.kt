package stock.me.symbols.search.provider

import stock.me.symbols.model.HistoricalQuote
import stock.me.symbols.model.Stock
import stock.me.symbols.model.StockQuote
import stock.me.symbols.model.SymbolSuggestion
import stock.me.symbols.search.service.SymbolSearchService

class DefaultSymbolSearchProvider(
    private val symbolService: SymbolSearchService
) : SymbolSearchProvider {

    override fun getSymbolSuggestions(query: String): List<SymbolSuggestion> =
        symbolService.getSymbolSuggestions(query)

    override fun getStockQuote(symbol: String): StockQuote =
        toStockQuoteDto(symbolService.getStockQuote(symbol))

    override fun getStockSymbolInformation(symbol: String): Stock =
        toStock(symbolService.getStockSymbolInformation(symbol))

    override fun getHistoricalQuotes(symbol: String): List<HistoricalQuote> =
        toHistoricalQuoteDto(symbolService.getHistoricalQuotes(symbol))

    override fun getStockQuotesOfTrendingSymbols(): List<StockQuote> =
        symbolService.getStockQuotesOfTrendingSymbols()
            .map { toStockQuoteDto(it) }
}