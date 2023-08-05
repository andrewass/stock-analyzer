package stock.client.backend.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import stock.client.backend.symbols.search.route.symbolSearchRoutes

fun Application.configureRouting() {
    routing {
        symbolSearchRoutes()
    }
}
