package stock.me.consumer

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class PolygonConsumer : StockConsumer {

    private val polygonApiKey = System.getenv("POLYGON_API_KEY")
    private val baseUrl = "https://api.polygon.io"

    override suspend fun getAllStockExchanges() {
        val client = HttpClient(CIO)
        val response: HttpResponse = client.get("$baseUrl/v1/meta/exchanges"){
            parameter("apiKey", polygonApiKey)
        }
        val stringBody : String = response.receive()
        println(stringBody)
    }

    override fun getAllSymbols() {

    }
}
