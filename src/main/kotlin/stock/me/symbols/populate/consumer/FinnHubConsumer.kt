package stock.me.symbols.populate.consumer

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import stock.me.model.Stock

class FinnHubConsumer : StockConsumer {

    private val finnHubApiKey = System.getenv("FINNHUB_API_KEY")
    private val baseUrl = "https://finnhub.io/api/v1"

    private val json = Json {
        ignoreUnknownKeys = true
    }
    private val client = HttpClient(CIO)

    override suspend fun getAllStocksFromExchange(exchange: String): List<Stock> {
        val response: HttpResponse = client.get("$baseUrl/stock/symbol") {
            parameter("exchange", exchange)
            parameter("token", finnHubApiKey)
        }
        return json.decodeFromString(ListSerializer(Stock.serializer()), response.receive())
    }
}
