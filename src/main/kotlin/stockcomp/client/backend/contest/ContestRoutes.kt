package stockcomp.client.backend.contest

import io.ktor.server.application.*
import io.ktor.server.routing.*
import stockcomp.client.backend.consumer.callResourceServerGet
import stockcomp.client.backend.consumer.callResourceServerPatch
import stockcomp.client.backend.consumer.callResourceServerPost

fun Route.contestRoutes() {
    val baseUrl = environment!!.config.propertyOrNull("contest-server.service")?.getString()

    route("/contest") {
        get("/active") {
            callResourceServerGet(call, "$baseUrl/contest/active")
        }

        get("/number"){
            callResourceServerGet(call, "$baseUrl/contest")
        }

        get("/sorted"){
            callResourceServerGet(call, "$baseUrl/contest/sorted")
        }

        post("/create") {
            callResourceServerPost(call, "$baseUrl/contest/create")
        }

        patch("/update") {
            callResourceServerPatch(call, "$baseUrl/contest/update")
        }
    }
}