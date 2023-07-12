package stock.me.symbols.search.consumer

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import stock.me.symbols.domain.CurrentPrice
import stock.me.symbols.search.consumer.request.CurrentPriceSymbolsRequest

class FastFinanceConsumer(
    private val client: HttpClient,
    private val baseUrl: String,
) : SymbolConsumer {

    override suspend fun getCurrentPriceSymbol(symbol: String): CurrentPrice =
        client.get("$baseUrl/current-price-symbol/$symbol").body()


    override suspend fun getCurrentPriceSymbols(symbols: List<String>): List<CurrentPrice> =
        client.post("$baseUrl/current-price-symbols") {
            setBody(CurrentPriceSymbolsRequest(symbols))
            contentType(ContentType.Application.Json)
        }.body()
}