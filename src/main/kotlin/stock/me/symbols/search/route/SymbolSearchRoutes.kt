package stock.me.symbols.search.route

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

import org.kodein.di.instance
import stock.me.config.kodein

fun Route.symbolSearchRoutes() {

    val serviceProvider by kodein.instance<SymbolSearchProvider>()

    route("/stock") {

        get("/symbol-information/{query}") {
            call.parameters["query"]
                ?.let { serviceProvider.getStockSymbolInformation(it) }
                ?.also { call.respond(it) }
                ?: return@get call.respond(HttpStatusCode.BadRequest)
        }

        get("/suggestions/{query}") {
            call.parameters["query"]
                ?.let { serviceProvider.getSymbolSuggestions(it) }
                ?.also { call.respond(it) }
                ?: return@get call.respond(HttpStatusCode.BadRequest)
        }

        get("/stock-quote/{symbol}") {
            call.parameters["symbol"]
                ?.let { serviceProvider.getStockQuote(it) }
                ?.also { call.respond(it) }
                ?: return@get call.respond(HttpStatusCode.BadRequest)
        }

        get("/stock-quote-trending") {
            serviceProvider.getStockQuotesOfTrendingSymbols()
                .also { call.respond(it) }
        }

        get("/historical-quotes/{symbol}") {
            call.parameters["symbol"]
                ?.let { serviceProvider.getHistoricalQuotes(it) }
                ?.also { call.respond(it) }
                ?: return@get call.respond(HttpStatusCode.BadRequest)
        }
    }
}
