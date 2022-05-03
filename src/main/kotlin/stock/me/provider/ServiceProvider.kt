package stock.me.provider

import stock.me.provider.response.HistoricalQuoteDto
import stock.me.provider.response.StockDto
import stock.me.provider.response.StockQuoteDto
import stock.me.service.symbolquery.SymbolSuggestion

interface ServiceProvider {

    fun getSymbolSuggestions(query: String): List<SymbolSuggestion>

    fun getStockSymbolInformation(symbol: String): StockDto

    fun getStockQuote(symbol: String): StockQuoteDto

    fun getHistoricalQuotes(symbol: String): List<HistoricalQuoteDto>

    fun getStockQuotesOfTrendingSymbols(): List<StockQuoteDto>
}
