package stockcomp.client.backend.investment

import io.ktor.server.routing.*
import stockcomp.client.backend.consumer.callResourceServerGet
import stockcomp.client.backend.consumer.callResourceServerPost

fun Route.investmentRoutes() {
    val baseUrl = environment.config.propertyOrNull("contest-server.service")?.getString()

    route("/investment") {
        get("/get-all") {
            callResourceServerGet(call, "$baseUrl/investment/get-all")
        }

        post("/get-by-symbol") {
            callResourceServerPost(call, "$baseUrl/investment/get-by-symbol")
        }
    }
}