package stockcomp.client.backend.investmentorder

import io.ktor.server.application.*
import io.ktor.server.routing.*
import stockcomp.client.backend.consumer.callResourceServerDelete
import stockcomp.client.backend.consumer.callResourceServerPost

fun Route.investmentOrderRoutes() {
    val baseUrl = environment!!.config.propertyOrNull("contest-server.service")?.getString()

    route("/investmentorder") {
        post("/place-order") {
            callResourceServerPost(call, "$baseUrl/investmentorder/place-order")
        }

        delete("/delete-order") {
            callResourceServerDelete(call, "$baseUrl/investmentorder/delete-order")
        }

        post("/get-by-status") {
            callResourceServerPost(call, "$baseUrl/investmentorder/get-by-status")
        }

        post("/get-all-by-status") {
            callResourceServerPost(call, "$baseUrl/investmentorder/get-all-by-status")
        }

        post("/get-by-status-symbol") {
            callResourceServerPost(call, "$baseUrl/investmentorder/get-by-status-symbol")
        }
    }
}