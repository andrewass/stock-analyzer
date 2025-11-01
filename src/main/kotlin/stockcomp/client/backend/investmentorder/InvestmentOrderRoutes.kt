package stockcomp.client.backend.investmentorder

import io.ktor.server.request.httpMethod
import io.ktor.server.request.uri
import io.ktor.server.routing.Route
import io.ktor.server.routing.route
import stockcomp.client.backend.consumer.forwardToResourceServer

fun Route.investmentOrderRoutes() {
    val baseUrl = environment.config.propertyOrNull("contest-server.service")?.getString() + "/investmentorders"

    route("/investmentorders/{...}") {
        handle {
            val relativePath = call.request.uri.removePrefix("/api/investmentorders")
            val targetUrl = "$baseUrl$relativePath"
            val method = call.request.httpMethod
            call.forwardToResourceServer(method, targetUrl)
        }
    }
}
