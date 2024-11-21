package stockcomp.client.backend.participant

import io.ktor.server.routing.*
import stockcomp.client.backend.consumer.callResourceServerGet
import stockcomp.client.backend.consumer.callResourceServerPost

fun Route.participantRoutes() {
    val baseUrl = environment.config.propertyOrNull("contest-server.service")?.getString() + "/participants"

    route("/participants") {
        get("/contest") {
            callResourceServerGet(call, "$baseUrl/contest")
        }

        get("/registered") {
            callResourceServerGet(call, "$baseUrl/registered")
        }

        get("/unregistered") {
            callResourceServerGet(call, "$baseUrl/unregistered")
        }

        post("/sign-up") {
            callResourceServerPost(call, "$baseUrl/sign-up")
        }

        get("/sorted") {
            callResourceServerGet(call, "$baseUrl/sorted")
        }

        get("/history") {
            callResourceServerGet(call, "$baseUrl/history")
        }
    }
}