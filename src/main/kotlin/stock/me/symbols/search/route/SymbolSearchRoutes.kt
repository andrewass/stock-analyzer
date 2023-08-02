package stock.me.symbols.search.route

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

import org.kodein.di.instance
import stock.me.config.kodein
import stock.me.symbols.search.domain.Period
import stock.me.symbols.search.service.SymbolSearchService

fun Route.symbolSearchRoutes() {
    val serviceProvider by kodein.instance<SymbolSearchProvider>()
    val symbolSearchService by kodein.instance<SymbolSearchService>()

    route("/stock") {
        get("/financial-details-symbol") {
            call.request.queryParameters["symbol"]
                ?.let { symbolSearchService.getStockSymbolFinancials(it) }
                ?.also { call.respond(it) }
        }

        get("/suggestions") {
            call.request.queryParameters["query"]
                ?.let { serviceProvider.getSymbolSuggestions(it) }
                ?.also { call.respond(it) }
                ?: return@get call.respond(HttpStatusCode.NotFound)
        }

        get("/current-price-symbol") {
            call.request.queryParameters["symbol"]
                ?.let { symbolSearchService.getCurrentPriceOfSymbol(it) }
                ?.also { call.respond(it) }
        }

        get("/current-price-trending-symbols") {
            symbolSearchService.getCurrentPriceOfTrendingSymbols()
                .also { call.respond(it) }
        }

        get("/historical-price") {
            val symbol = call.request.queryParameters["symbol"]!!
            val period: Period = Period.valueOf(call.request.queryParameters["period"]!!)
            symbolSearchService.getHistoricalPrice(symbol, period)
                .also { call.respond(it) }
        }
    }
}
