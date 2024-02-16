package stockcomp.client.backend.contest.route

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import stockcomp.client.backend.config.HttpClient
import stockcomp.client.backend.plugins.UserSession

fun Route.contestRoutes() {
    val baseUrl = environment!!.config.propertyOrNull("contest-server.service")?.getString()

    route("/contest") {
        post("/get-by-status") {
            val userSession: UserSession? = call.sessions.get()
            val response = HttpClient.client.post("$baseUrl/contest/get-by-status") {
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer ${userSession!!.accessToken}")
                setBody(call.receiveText())
            }
            call.respondText(response.body())
        }

        post("/create") {
            val userSession: UserSession? = call.sessions.get()
            val response = HttpClient.client.post("$baseUrl/contest/create") {
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer ${userSession!!.accessToken}")
                setBody(call.receiveText())
            }
            call.respond(HttpStatusCode.Created)
        }
    }
}