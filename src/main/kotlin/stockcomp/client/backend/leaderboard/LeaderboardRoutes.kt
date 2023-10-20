package stockcomp.client.backend.leaderboard

import io.ktor.server.application.*
import io.ktor.server.routing.*


fun Route.leaderboardRoutes() {

    route("/api/leaderboard") {
        get("/sorted-entries") {
        }

        get("/user-entry") {
            val token = call.request.cookies["AUTH_SESSION"]
        }
    }
}