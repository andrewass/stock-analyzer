package stockcomp.client.backend.leaderboard.route

import io.ktor.server.application.*
import io.ktor.server.routing.*
import stockcomp.client.backend.consumer.callResourceServerGet


fun Route.leaderboardRoutes() {
    val baseUrl = environment!!.config.propertyOrNull("contest-server.service")?.getString()

    route("/leaderboard") {
        get("/sorted-entries") {
            callResourceServerGet(call, "$baseUrl/leaderboard/sorted-entries")
        }

        get("/user-entry") {
            callResourceServerGet(call, "$baseUrl/leaderboard/user-entry")
        }
    }
}