package stockcomp.client.backend.symbols.populate.consumer

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import stockcomp.client.backend.symbols.domain.Stock

class FinnHubConsumer(
    private val httpClient: HttpClient,
) : SymbolPopulatorConsumer {
    private val finnHubApiKey = System.getenv("FINNHUB_API_KEY")
    private val baseUrl = "https://finnhub.io/api/v1"

    override suspend fun getAllSymbolsFromExchange(exchange: String): List<Stock> {
        val response: HttpResponse =
            httpClient.get("$baseUrl/stock/symbol") {
                parameter("exchange", exchange)
                parameter("token", finnHubApiKey)
            }
        return response.body()
    }
}
