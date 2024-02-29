package stockcomp.client.backend.participant

import io.ktor.server.application.*
import io.ktor.server.routing.*
import stockcomp.client.backend.consumer.callResourceServerGet
import stockcomp.client.backend.consumer.callResourceServerPost

fun Route.participantRoutes() {
    val baseUrl = environment!!.config.propertyOrNull("contest-server.service")?.getString()

    route("/participant") {
        get("/by-contest") {
            callResourceServerGet(call, "$baseUrl/participant/participant-by-contest")
        }

        get("/by-active-contest") {
            callResourceServerGet(call, "$baseUrl/participant/participant-by-active-contest")
        }

        post("/sign-up") {
            callResourceServerPost(call, "$baseUrl/participant/sign-up-participant")
        }

        get("/sorted") {
            callResourceServerGet(call, "$baseUrl/participant/sorted-participants")
        }

        get("/history") {
            callResourceServerGet(call, "$baseUrl/participant/detailed-participant-history")
        }
    }
}