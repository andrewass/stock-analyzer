package stock.me.routes

import io.ktor.application.*
import io.ktor.routing.*

fun Application.registerRoutes() {

    routing {
        symbolSearchRoutes()
    }
}