package stockcomp.client.backend.participant

import io.ktor.server.application.*
import io.ktor.server.routing.*
import stockcomp.client.backend.consumer.callResourceServerGet
import stockcomp.client.backend.consumer.callResourceServerPost

fun Route.participantRoutes() {
    val baseUrl = environment!!.config.propertyOrNull("contest-server.service")?.getString() + "/participants"

    route("/participants") {
        get("/contest") {
            callResourceServerGet(call, "$baseUrl/contest")
        }

        post("/sign-up") {
            callResourceServerPost(call, "$baseUrl/sign-up-participant")
        }

        get("/sorted") {
            callResourceServerGet(call, "$baseUrl/sorted")
        }

        get("/history") {
            callResourceServerGet(call, "$baseUrl/history")
        }

        get("/running-participants"){
            callResourceServerGet(call, "$baseUrl/running-participants")
        }
    }
}