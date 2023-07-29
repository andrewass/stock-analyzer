package stock.me.symbols.search.consumer

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import stock.me.symbols.domain.SymbolFinancials
import stock.me.symbols.search.consumer.request.CurrentPriceResponse
import stock.me.symbols.search.consumer.request.CurrentPriceSymbolsRequest

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
}