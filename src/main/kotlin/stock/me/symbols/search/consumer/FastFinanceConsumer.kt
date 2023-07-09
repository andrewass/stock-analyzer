package stock.me.symbols.search.consumer

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

import stock.me.symbols.search.consumer.request.CurrentPriceSymbolsRequest

class FastFinanceConsumer(
    private val client: HttpClient,
    private val baseUrl: String,
) : SymbolConsumer {

    override fun getCurrentPriceSymbol(symbol: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentPriceSymbols(symbols: List<String>) {
        val response = client.post("$baseUrl/current-price-symbols") {
            setBody(CurrentPriceSymbolsRequest(symbols))
            contentType(ContentType.Application.Json)
        }
    }
}