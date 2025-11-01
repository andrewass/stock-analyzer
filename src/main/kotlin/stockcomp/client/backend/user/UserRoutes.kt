package stockcomp.client.backend.user

import io.ktor.server.request.httpMethod
import io.ktor.server.request.uri
import io.ktor.server.routing.Route
import io.ktor.server.routing.route
import stockcomp.client.backend.consumer.forwardToResourceServer

fun Route.userRoutes() {
    val baseUrl = environment.config.propertyOrNull("contest-server.service")?.getString() + "/user"

    route("/user/{...}") {
        handle {
            val relativePath = call.request.uri.removePrefix("/api/user")
            val targetUrl = "$baseUrl$relativePath"
            val method = call.request.httpMethod
            call.forwardToResourceServer(method, targetUrl)
        }
    }
}
