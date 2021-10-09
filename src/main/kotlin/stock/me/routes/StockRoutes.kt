package stock.me.routes

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.elasticsearch.client.RestHighLevelClient
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import stock.me.service.SymbolSearchService

fun Route.stockRoute() {
    val restClient by closestDI().instance<RestHighLevelClient>()
    val symbolSearchService by closestDI().instance<SymbolSearchService>()

    route("/stock") {

        get("/suggestions/{query}") {
            val query = call.parameters["query"] ?: return@get call.respond(HttpStatusCode.BadRequest)
            val suggestions = symbolSearchService.getSymbolSuggestions(restClient, query)
            call.respond(suggestions)
        }

        get("/stock-quote/{symbol}") {
            val symbol = call.parameters["symbol"] ?: return@get call.respond(HttpStatusCode.BadRequest)
            val stockQuote = symbolSearchService.getStockQuote(symbol)
            call.respond(stockQuote)
        }

        get("/stock-quote-trending") {
            val stockQuotes = symbolSearchService.getStockQuotesOfTrendingSymbols()
            call.respond(stockQuotes)
        }

        get("/historical-quotes/{symbol}") {
            val symbol = call.parameters["symbol"] ?: return@get call.respond(HttpStatusCode.BadRequest)
            val historicalQuotes = symbolSearchService.getHistoricalQuotes(symbol)
            call.respond(historicalQuotes)
        }
    }
}
