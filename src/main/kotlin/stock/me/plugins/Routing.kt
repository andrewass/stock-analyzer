package stock.me.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import stock.me.symbols.search.route.symbolSearchRoutes

fun Application.configureRouting() {
    routing {
        symbolSearchRoutes()
    }
}
