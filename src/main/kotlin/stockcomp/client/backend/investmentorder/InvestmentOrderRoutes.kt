package stockcomp.client.backend.investmentorder

import io.ktor.server.application.*
import io.ktor.server.routing.*
import stockcomp.client.backend.consumer.callResourceServerDelete
import stockcomp.client.backend.consumer.callResourceServerGet
import stockcomp.client.backend.consumer.callResourceServerPost

fun Route.investmentOrderRoutes() {
    val baseUrl = environment!!.config.propertyOrNull("contest-server.service")?.getString()

    route("/investmentorder") {
        post("/post") {
            callResourceServerPost(call, "$baseUrl/investmentorder/post")
        }

        delete("/delete") {
            callResourceServerDelete(call, "$baseUrl/investmentorder/delete")
        }

        get("/all-active") {
            callResourceServerGet(call, "$baseUrl/investmentorder/all-active")
        }

        get("/all-completed") {
            callResourceServerGet(call, "$baseUrl/investmentorder/all-completed")
        }

        get("/symbol-active") {
            callResourceServerGet(call, "$baseUrl/investmentorder/symbol-active")
        }

        get("/symbol-completed") {
            callResourceServerGet(call, "$baseUrl/investmentorder/symbol-completed")
        }
    }
}