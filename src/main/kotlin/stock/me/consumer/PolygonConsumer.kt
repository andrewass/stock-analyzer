package stock.me.consumer

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import stock.me.model.Exchange
import stock.me.model.Stock

class PolygonConsumer : StockConsumer {

    private val polygonApiKey = System.getenv("POLYGON_API_KEY")
    private val baseUrl = "https://api.polygon.io"

    private val json = Json {
        ignoreUnknownKeys = true
    }
    private val client = HttpClient(CIO)

    override suspend fun getAllStockExchanges(): List<Exchange> {
        val response: HttpResponse = client.get("$baseUrl/v1/meta/exchanges") {
            parameter("apiKey", polygonApiKey)
        }
        return json.decodeFromString(ListSerializer(Exchange.serializer()), response.receive())
    }

    override suspend fun getAllStocks(nextPageUrl: String?): Pair<List<Stock>, String?> {
        val response: HttpResponse = if (nextPageUrl == null) {
            client.get("$baseUrl/v3/reference/tickers") {
                parameter("market", "stocks")
                parameter("limit", 10)
                parameter("apiKey", polygonApiKey)
            }
        } else {
            client.get("$baseUrl/v1/meta/exchanges") {
                parameter("apiKey", polygonApiKey)
            }
        }
        val jsonResponse = (Json.parseToJsonElement(response.receive()) as JsonObject)
        val nextPage = jsonResponse["next_url"]?.toString()
        val stocks = jsonResponse["results"]!!.let {
            json.decodeFromJsonElement(ListSerializer(Stock.serializer()), it)
        }
        return Pair(stocks, nextPage)
    }
}
