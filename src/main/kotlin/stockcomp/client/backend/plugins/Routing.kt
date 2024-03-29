package stockcomp.client.backend.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import stockcomp.client.backend.account.accountRoutes
import stockcomp.client.backend.authentication.customAuthRoutes
import stockcomp.client.backend.contest.contestRoutes
import stockcomp.client.backend.investment.investmentRoutes
import stockcomp.client.backend.investmentorder.investmentOrderRoutes
import stockcomp.client.backend.leaderboard.route.leaderboardRoutes
import stockcomp.client.backend.participant.participantRoutes
import stockcomp.client.backend.symbols.search.route.symbolSearchRoutes

fun Application.configureRouting() {
    routing {
        route("/api") { customAuthRoutes() }
        route("/api") { symbolSearchRoutes() }
        route("/api") { leaderboardRoutes() }
        route("/api") { contestRoutes() }
        route("/api") { investmentRoutes() }
        route("/api") { investmentOrderRoutes() }
        route("/api") { participantRoutes() }
        route("/api") { accountRoutes() }
    }
}
