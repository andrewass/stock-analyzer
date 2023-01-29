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

        get("/symbol-information") {
            call.request.queryParameters["symbol"]
                ?.let { serviceProvider.getStockDetails(it) }
                ?.also { call.respond(it) }
                ?: return@get call.respond(HttpStatusCode.NotFound)
        }

        get("/suggestions") {
            call.request.queryParameters["query"]
                ?.let { serviceProvider.getSymbolSuggestions(it) }
                ?.also { call.respond(it) }
                ?: return@get call.respond(HttpStatusCode.NotFound)
        }

        get("/stock-quote") {
            call.request.queryParameters["symbol"]
                ?.let { serviceProvider.getRealTimePrice(it) }
                ?.also { call.respond(it) }
                ?: return@get call.respond(HttpStatusCode.NotFound)
        }

        get("/stock-quote-trending") {
            serviceProvider.getStockQuotesOfTrendingSymbols()
                .also { call.respond(it) }
        }

        get("/historical-quotes") {
            call.request.queryParameters["symbol"]
                ?.let { serviceProvider.getHistoricalQuotes(it) }
                ?.also { call.respond(it) }
                ?: return@get call.respond(HttpStatusCode.NotFound)
        }
    }
}
