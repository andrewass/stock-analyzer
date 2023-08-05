package stock.client.backend.symbols.search.consumer

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import stock.client.backend.symbols.domain.HistoricalPrice
import stock.client.backend.symbols.domain.SymbolFinancials
import stock.client.backend.symbols.search.domain.CurrentPriceResponse
import stock.client.backend.symbols.search.domain.CurrentPriceSymbolsRequest

class FastFinanceConsumer(
    private val client: HttpClient,
    private val baseUrl: String,
) : SymbolConsumer {

    override suspend fun getCurrentPriceSymbol(symbol: String): CurrentPriceResponse =
        client.get("$baseUrl/price/current-price-symbol/$symbol").body()


    override suspend fun getCurrentPriceSymbols(symbols: List<String>): List<CurrentPriceResponse> =
        client.post("$baseUrl/price/current-price-symbols") {
            setBody(CurrentPriceSymbolsRequest(symbols))
            contentType(ContentType.Application.Json)
        }.body()


    override suspend fun getFinancialsSymbol(symbol: String): SymbolFinancials =
        client.get("$baseUrl/financials/financial-details-symbol/$symbol").body()


    override suspend fun getHistoricalPriceSymbol(symbol: String, period: stock.client.backend.symbols.search.domain.Period): List<HistoricalPrice> =
        client.get(baseUrl) {
            url {
                appendPathSegments("price", "historical-prices-symbol")
                parameters.append("symbol", symbol)
                parameters.append("period", period.decode)
            }
        }.body()
}