package stock.me.symbols.populate.consumer

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.jackson.*
import stock.me.symbols.model.Stock

class FinnHubConsumer : StockConsumer {

    private val finnHubApiKey = System.getenv("FINNHUB_API_KEY")
    private val baseUrl = "https://finnhub.io/api/v1"

    private val client = HttpClient(CIO){
        install(ContentNegotiation){
            jackson {
                registerModule(JavaTimeModule())
            }
        }
    }

    override suspend fun getAllStocksFromExchange(exchange: String): List<Stock> {
        val response: HttpResponse = client.get("$baseUrl/stock/symbol", ) {
            parameter("exchange", exchange)
            parameter("token", finnHubApiKey)
        }
        return response.body()
    }
}
