package stockcomp.client.backend.contest.route

import io.ktor.server.application.*
import io.ktor.server.routing.*
import stockcomp.client.backend.consumer.callResourceServerPatch
import stockcomp.client.backend.consumer.callResourceServerPost

fun Route.contestRoutes() {
    val baseUrl = environment!!.config.propertyOrNull("contest-server.service")?.getString()

    route("/contest") {
        post("/get-by-status") {
            callResourceServerPost(call, "$baseUrl/contest/get-by-status")
        }

        post("/create") {
            callResourceServerPost(call, "$baseUrl/contest/create")
        }

        patch("/update") {
            callResourceServerPatch(call, "$baseUrl/contest/update")
        }
    }
}