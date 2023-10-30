package stockcomp.client.backend.leaderboard

import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.util.*
import stockcomp.client.backend.config.HttpClient
import stockcomp.client.backend.plugins.UserSession


@OptIn(InternalAPI::class)
fun Route.leaderboardRoutes() {
    val baseUrl = environment!!.config.propertyOrNull("contest-server.service")?.getString()

    route("/api/leaderboard") {
        get("/sorted-entries") {
        }

        get("/user-entry") {
            val userSession: UserSession? = call.sessions.get()
            println("USER SESSION IS -------------------------------- $userSession")
            val request = call.receive<String>()

            val response = HttpClient.client.get("$baseUrl/leaderboard/sorted-entries") {
                header(HttpHeaders.Authorization, "Bearer ${userSession!!.token}")
                body = request
            }
            call.respond(response.content)
        }
    }
}