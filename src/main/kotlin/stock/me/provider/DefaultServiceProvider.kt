package stock.me.provider

import kotlinx.serialization.json.JsonElement
import org.kodein.di.instance
import stock.me.config.kodein
import stock.me.service.SymbolSearchService
import stock.me.provider.response.HistoricalQuoteDto
import stock.me.provider.response.StockQuoteDto


class DefaultServiceProvider : ServiceProvider{

    private val symbolSearchService by kodein.instance<SymbolSearchService>()

    override fun getSymbolSuggestions(query: String): List<JsonElement> {
        return symbolSearchService.getSymbolSuggestions(query)
    }

    override fun getStockQuote(symbol: String): StockQuoteDto {
        TODO("Not yet implemented")
    }

    override fun getHistoricalQuotes(symbol: String): List<HistoricalQuoteDto> {
        TODO("Not yet implemented")
    }

    override fun getStockQuotesOfTrendingSymbols(): List<StockQuoteDto> {
        TODO("Not yet implemented")
    }
}