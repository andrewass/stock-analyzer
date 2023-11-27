package stockcomp.client.backend.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import stockcomp.client.backend.authentication.customAuthRoutes
import stockcomp.client.backend.contest.route.contestRoutes
import stockcomp.client.backend.leaderboard.route.leaderboardRoutes
import stockcomp.client.backend.symbols.search.route.symbolSearchRoutes

fun Application.configureRouting() {
    routing {
        route("/api") { customAuthRoutes() }
        route("/api") { symbolSearchRoutes() }
        route("/api") { leaderboardRoutes() }
        route("/api") { contestRoutes() }
    }
}
