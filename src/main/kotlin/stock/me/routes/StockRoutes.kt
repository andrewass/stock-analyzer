package stock.me.routes

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.elasticsearch.client.RestHighLevelClient
import org.kodein.di.instance
import org.kodein.di.ktor.di
import stock.me.service.SymbolSearchService

fun Route.stockRoute() {
    val restClient by di().instance<RestHighLevelClient>()
    val entitySearchService by di().instance<SymbolSearchService>()

    route("/stock") {
        get("/suggestions/{query}") {
            val query = call.parameters["query"] ?: return@get call.respond(HttpStatusCode.BadRequest)
            val result = entitySearchService.getSymbolSuggestions(restClient, query)
            call.respond(result)
        }

        get("/stock-quote/{symbol}") {
            val symbol = call.parameters["symbol"] ?: return@get call.respond(HttpStatusCode.BadRequest)
            val result = entitySearchService.getStockQuote(symbol)
            call.respond(result)
        }

        get("/historical-quotes/{symbol}"){
            val symbol = call.parameters["symbol"] ?: return@get call.respond(HttpStatusCode.BadRequest)
            val result = entitySearchService.getHistoricalQuotes(symbol)
            call.respond(result)
        }
    }
}
