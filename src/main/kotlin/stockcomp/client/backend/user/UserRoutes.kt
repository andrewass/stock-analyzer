package stockcomp.client.backend.user

import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import stockcomp.client.backend.consumer.callResourceServerGet

fun Route.userRoutes() {
    val baseUrl = environment.config.propertyOrNull("contest-server.service")?.getString() + "/user"

    route("/user") {
        get("/sorted") {
            callResourceServerGet(call, "$baseUrl/sorted")
        }
    }
}
