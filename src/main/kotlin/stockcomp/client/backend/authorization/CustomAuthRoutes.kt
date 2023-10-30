package stockcomp.client.backend.authorization

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import stockcomp.client.backend.plugins.UserSession


fun Route.customAuthRoutes() {
    route("/api") {
        authenticate("custom-oauth2") {
            get("/login") {}

            get("/callback") {
                val principal: OAuthAccessTokenResponse.OAuth2? = call.authentication.principal()
                call.sessions.set(UserSession(principal!!.state!!, principal.accessToken))
                call.respondRedirect("/symbols")
            }
        }
    }
}