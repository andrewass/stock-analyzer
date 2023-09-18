package stockcomp.client.backend.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import stockcomp.client.backend.authorization.googleAuthRoutes
import stockcomp.client.backend.symbols.search.route.symbolSearchRoutes

fun Application.configureRouting() {
    routing {
        googleAuthRoutes()
        symbolSearchRoutes()
    }
}
