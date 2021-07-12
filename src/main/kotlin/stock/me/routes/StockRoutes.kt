package stock.me.routes

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.di
import stock.me.model.Stock

fun Route.stockRoute() {

    route("/stock/real-time-price/{symbol}") {
        get {
            val realTimePrice = emptyList<Stock>()
            call.respond(realTimePrice)
        }

        get("{symbol}") {
            val symbol = call.parameters["symbol"] ?: return@get call.respondText(
                "Missing or malformed symbol", status = HttpStatusCode.BadRequest
            )
            call.respond("")
        }
    }
}
