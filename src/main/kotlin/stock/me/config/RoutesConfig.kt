package stock.me.routes

import io.ktor.server.application.*
import io.ktor.server.routing.*
import stock.me.symbols.search.route.symbolSearchRoutes

fun Application.registerRoutes() {

    routing {
        symbolSearchRoutes()
    }
}