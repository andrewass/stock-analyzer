package stockcomp.client.backend.account

import io.ktor.server.application.*
import io.ktor.server.routing.*
import stockcomp.client.backend.consumer.callResourceServerGet
import stockcomp.client.backend.consumer.callResourceServerPatch

fun Route.accountRoutes() {
    val baseUrl = environment!!.config.propertyOrNull("contest-server.service")?.getString()

    route("/user") {
        get("/sorted") {
            callResourceServerGet(call, "$baseUrl/user/get-all-sorted")
        }

        patch("/update") {
            callResourceServerPatch(call, "$baseUrl/user/update-details")
        }

        get("/details") {
            callResourceServerGet(call, "$baseUrl/user/get-details")
        }
    }
}