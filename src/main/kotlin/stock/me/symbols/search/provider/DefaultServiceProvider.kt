package stock.me.symbols.search.provider

import stock.me.provider.response.HistoricalQuoteDto
import stock.me.provider.response.StockDto
import stock.me.provider.response.StockQuoteDto
import stock.me.provider.toHistoricalQuoteDto
import stock.me.provider.toStockDto
import stock.me.provider.toStockQuoteDto
import stock.me.symbols.search.SymbolSuggestion
import stock.me.symbols.search.service.SymbolQueryService

class DefaultServiceProvider(
    private val symbolService: SymbolQueryService
) : ServiceProvider {

    override fun getSymbolSuggestions(query: String): List<SymbolSuggestion> =
        symbolService.getSymbolSuggestions(query)

    override fun getStockQuote(symbol: String): StockQuoteDto =
        toStockQuoteDto(symbolService.getStockQuote(symbol))

    override fun getStockSymbolInformation(symbol: String): StockDto =
        toStockDto(symbolService.getStockSymbolInformation(symbol))

    override fun getHistoricalQuotes(symbol: String): List<HistoricalQuoteDto> =
        toHistoricalQuoteDto(symbolService.getHistoricalQuotes(symbol))

    override fun getStockQuotesOfTrendingSymbols(): List<StockQuoteDto> =
        symbolService.getStockQuotesOfTrendingSymbols()
            .map { toStockQuoteDto(it) }
}