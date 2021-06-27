package stock.me.routes

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.di
import stock.me.service.StockService

fun Route.stockRoute() {

    val stockService by di().instance<StockService>()

    route("/stock/get-all") {
        get {
            val stocks = stockService.getAllStocks()
            call.respond(stocks)
        }

        get("{symbol}") {
            val symbol = call.parameters["symbol"] ?: return@get call.respondText(
                "Missing or malformed symbol", status = HttpStatusCode.BadRequest
            )
            val stock = stockService ?: return@get call.respondText(
                "No stock with symbol $symbol found", status = HttpStatusCode.NotFound
            )
            call.respond(stock)
        }
    }
}
