package stockcomp.client.backend.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import stockcomp.client.backend.authorization.customAuthRoutes
import stockcomp.client.backend.leaderboard.leaderboardRoutes
import stockcomp.client.backend.symbols.search.route.symbolSearchRoutes

fun Application.configureRouting() {
    routing {
        customAuthRoutes()
        symbolSearchRoutes()
        leaderboardRoutes()
    }
}
