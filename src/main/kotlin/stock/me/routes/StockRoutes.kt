package stock.me.routes

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import stock.me.models.Stock
import stock.me.models.stockStorage

fun Route.stockRoute() {
    route("/stock") {
        get {
            if (stockStorage.isNotEmpty()) {
                call.respond(stockStorage)
            } else {
                call.respondText("No stocks found")
            }
        }

        get("{symbol}") {
            val symbol = call.parameters["symbol"] ?: return@get call.respondText(
                "Missing or malformed symbol", status = HttpStatusCode.BadRequest
            )
            val stock = stockStorage.find { it.symbol == symbol } ?: return@get call.respondText(
                "No stock with symbol $symbol found", status = HttpStatusCode.NotFound
            )
            call.respond(stock)
        }

        post {
            val stock = call.receive<Stock>()
            stockStorage.add(stock)
            call.respondText("Stock stored", status = HttpStatusCode.Created)
        }
    }
}
