package stockcomp.client.backend.authentication

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import stockcomp.client.backend.authentication.dto.ValidSession
import stockcomp.client.backend.plugins.UserSession


fun Route.customAuthRoutes() {
    route("/api") {
        authenticate("google-oauth-auth") {
            get("/login") {}

            get("/callback") {
                val principal: OAuthAccessTokenResponse.OAuth2? = call.authentication.principal()
                call.sessions.set(UserSession(principal!!.state!!, principal.accessToken))
                call.respondRedirect("/symbols")
            }
        }

        post("/logout") {
            call.sessions.clear<UserSession>()
            call.response.status(HttpStatusCode.OK)
        }

        get("/valid-session") {
            val userSession: UserSession? = call.sessions.get()
            call.respond(ValidSession(userSession != null))
        }
    }
}