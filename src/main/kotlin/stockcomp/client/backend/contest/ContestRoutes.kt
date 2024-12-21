package stockcomp.client.backend.contest

import io.ktor.server.routing.*
import stockcomp.client.backend.consumer.callResourceServerDelete
import stockcomp.client.backend.consumer.callResourceServerGet
import stockcomp.client.backend.consumer.callResourceServerPatch
import stockcomp.client.backend.consumer.callResourceServerPost

fun Route.contestRoutes() {
    val baseUrl = environment.config.propertyOrNull("contest-server.service")?.getString() + "/contests"

    route("/contests") {

        get("/{contestId}") {
            callResourceServerGet(call, "$baseUrl/${call.parameters["contestId"]}")
        }

        delete("/{contestId}"){
            callResourceServerDelete(call, "$baseUrl/${call.parameters["contestId"]}")
        }

        get("/active") {
            callResourceServerGet(call, "$baseUrl/active")
        }

        get("/registered") {
            callResourceServerGet(call, "$baseUrl/registered")
        }

        get("/unregistered") {
            callResourceServerGet(call, "$baseUrl/unregistered")
        }

        get("/number") {
            callResourceServerGet(call, baseUrl)
        }

        get("/all") {
            callResourceServerGet(call, "$baseUrl/all")
        }

        post("/create") {
            callResourceServerPost(call, "$baseUrl/create")
        }

        post("/update") {
            callResourceServerPatch(call, "$baseUrl/update")
        }

        post("/sign-up/{contestId}"){
            callResourceServerPost(call, "$baseUrl/sign-up/${call.parameters["contestId"]}")
        }
    }
}
