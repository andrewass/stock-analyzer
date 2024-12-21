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

        post("/sign-up/{contestId}") {
            callResourceServerPost(call, "$baseUrl/sign-up/${call.parameters["contestId"]}")
        }

        get("/sorted") {
            callResourceServerGet(call, "$baseUrl/sorted")
        }

        get("/history") {
            callResourceServerGet(call, "$baseUrl/history")
        }
    }
}
