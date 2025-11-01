package stockcomp.client.backend.contest

import io.ktor.server.request.httpMethod
import io.ktor.server.request.uri
import io.ktor.server.routing.Route
import io.ktor.server.routing.route
import stockcomp.client.backend.consumer.forwardToResourceServer

fun Route.contestRoutes() {
    val baseUrl = environment.config.propertyOrNull("contest-server.service")?.getString() + "/contests"

    route("/contests/{...}") {
        handle {
            val relativePath = call.request.uri.removePrefix("/api/contests")
            val targetUrl = "$baseUrl$relativePath"
            val method = call.request.httpMethod
            call.forwardToResourceServer(method, targetUrl)
        }
    }
}
