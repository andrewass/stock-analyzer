package stock.me.routes

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.di.instance
import stock.me.config.kodein
import stock.me.symbols.search.provider.SymbolSearchProvider

fun Route.symbolSearchRoutes() {

    val serviceProvider by kodein.instance<SymbolSearchProvider>()

    route("/stock") {

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
