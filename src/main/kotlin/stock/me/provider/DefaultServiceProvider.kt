package stock.me.provider

import kotlinx.serialization.json.JsonElement
import org.kodein.di.instance
import stock.me.config.kodein
import stock.me.provider.response.HistoricalQuoteDto
import stock.me.provider.response.StockQuoteDto
import stock.me.service.SymbolService


class DefaultServiceProvider : ServiceProvider {

    private val symbolSearchService by kodein.instance<SymbolService>()

    override fun getSymbolSuggestions(query: String): List<JsonElement> =
        symbolSearchService.getSymbolSuggestions(query)

    override fun getStockQuote(symbol: String): StockQuoteDto =
        toStockQuoteDto(symbolSearchService.getStockQuote(symbol))

    override fun getHistoricalQuotes(symbol: String): List<HistoricalQuoteDto> =
        toHistoricalQuoteDto(symbolSearchService.getHistoricalQuotes(symbol))

    override fun getStockQuotesOfTrendingSymbols(): List<StockQuoteDto> =
        symbolSearchService.getStockQuotesOfTrendingSymbols()
            .map { toStockQuoteDto(it) }
}