package stock.me.provider

import stock.me.provider.response.HistoricalQuoteDto
import stock.me.provider.response.StockDto
import stock.me.provider.response.StockQuoteDto
import stock.me.service.symbolquery.SymbolQueryService
import stock.me.service.symbolquery.SymbolSuggestion


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