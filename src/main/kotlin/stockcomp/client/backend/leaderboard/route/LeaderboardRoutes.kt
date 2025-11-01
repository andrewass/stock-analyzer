package stockcomp.client.backend.leaderboard.route

import io.ktor.server.request.httpMethod
import io.ktor.server.request.uri
import io.ktor.server.routing.Route
import io.ktor.server.routing.route
import stockcomp.client.backend.consumer.forwardToResourceServer

fun Route.leaderboardRoutes() {
    val baseUrl = environment.config.propertyOrNull("contest-server.service")?.getString()

    route("/leaderboard/{...}") {
        handle {
            val relativePath = call.request.uri.removePrefix("/api/leaderboard")
            val targetUrl = "$baseUrl$relativePath"
            val method = call.request.httpMethod
            call.forwardToResourceServer(method, targetUrl)
        }
    }
}
