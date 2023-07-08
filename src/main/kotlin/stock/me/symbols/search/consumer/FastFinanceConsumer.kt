package stock.me.symbols.search.consumer

import io.ktor.client.*
import io.ktor.client.request.*
import stock.me.symbols.search.consumer.request.CurrentPriceSymbolsRequest

class FastFinanceConsumer(
    private val client: HttpClient
) : SymbolConsumer {

    private val baseUrl = "http://fastfinance-service:8079"

    override fun getCurrentPriceSymbol(symbol: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentPriceSymbols(symbols: List<String>) {
        client.post("$baseUrl/current-price-symbols"){
            setBody(CurrentPriceSymbolsRequest(symbols))
        }
    }
}