package stockcomp.client.backend.investmentorder

import io.ktor.server.routing.*
import stockcomp.client.backend.consumer.callResourceServerDelete
import stockcomp.client.backend.consumer.callResourceServerGet
import stockcomp.client.backend.consumer.callResourceServerPost

fun Route.investmentOrderRoutes() {
    val baseUrl = environment.config.propertyOrNull("contest-server.service")?.getString() + "/investmentorder"

    route("/investmentorder") {
        post("/post") {
            callResourceServerPost(call, "$baseUrl/post")
        }

        delete("/delete") {
            callResourceServerDelete(call, "$baseUrl/delete")
        }

        get("/all-active") {
            callResourceServerGet(call, "$baseUrl/all-active")
        }

        get("/all-completed") {
            callResourceServerGet(call, "$baseUrl/all-completed")
        }

        get("/symbol-active") {
            callResourceServerGet(call, "$baseUrl/symbol-active")
        }

        get("/symbol-completed") {
            callResourceServerGet(call, "$baseUrl/symbol-completed")
        }
    }
}
