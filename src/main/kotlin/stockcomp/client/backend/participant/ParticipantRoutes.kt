package stockcomp.client.backend.participant

import io.ktor.server.request.httpMethod
import io.ktor.server.request.uri
import io.ktor.server.routing.Route
import io.ktor.server.routing.route
import stockcomp.client.backend.consumer.forwardToResourceServer

fun Route.participantRoutes() {
    val baseUrl = environment.config.propertyOrNull("contest-server.service")?.getString() + "/participants"

    route("/participants/{...}") {
        handle {
            val relativePath = call.request.uri.removePrefix("/api/participants")
            val targetUrl = "$baseUrl$relativePath"
            val method = call.request.httpMethod
            call.forwardToResourceServer(method, targetUrl)
        }
    }
}
