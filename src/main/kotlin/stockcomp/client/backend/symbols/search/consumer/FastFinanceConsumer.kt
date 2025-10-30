package stockcomp.client.backend.symbols.search.consumer

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import stockcomp.client.backend.symbols.domain.HistoricalPriceResponse
import stockcomp.client.backend.symbols.domain.SymbolFinancials
import stockcomp.client.backend.symbols.search.domain.CurrentPriceResponse
import stockcomp.client.backend.symbols.search.domain.CurrentPriceSymbolsRequest
import stockcomp.client.backend.symbols.search.domain.Period

interface SymbolSearchConsumer {
    suspend fun getCurrentPriceSymbol(symbol: String): CurrentPriceResponse

    suspend fun getCurrentPriceSymbols(symbols: List<String>): List<CurrentPriceResponse>

    suspend fun getFinancialsSymbol(symbol: String): SymbolFinancials

    suspend fun getHistoricalPriceSymbol(
        symbol: String,
        period: Period,
    ): HistoricalPriceResponse
}

class FastFinanceConsumer(
    private val client: HttpClient,
    private val baseUrl: String,
) : SymbolSearchConsumer {
    override suspend fun getCurrentPriceSymbol(symbol: String): CurrentPriceResponse =
        client.get("$baseUrl/price/current-price/$symbol").body()

    override suspend fun getCurrentPriceSymbols(symbols: List<String>): List<CurrentPriceResponse> =
        client
            .post("$baseUrl/price/current-price-symbols") {
                setBody(CurrentPriceSymbolsRequest(symbols))
                contentType(ContentType.Application.Json)
            }.body()

    override suspend fun getHistoricalPriceSymbol(
        symbol: String,
        period: Period,
    ): HistoricalPriceResponse =
        client
            .get(baseUrl) {
                url {
                    appendPathSegments("price", "historical-prices")
                    parameters.append("symbol", symbol)
                    parameters.append("period", period.decode)
                }
            }.body()

    override suspend fun getFinancialsSymbol(symbol: String): SymbolFinancials = client.get("$baseUrl/statistics/$symbol").body()
}
