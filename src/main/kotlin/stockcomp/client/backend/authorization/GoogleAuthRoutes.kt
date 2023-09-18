package stockcomp.client.backend.authorization

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import stockcomp.client.backend.plugins.UserSession

val redirects = mutableMapOf<String, String>()

fun Route.googleAuthRoutes(){
    authenticate("auth-oauth-google") {
        get("/login") {
            // Redirects to 'authorizeUrl' automatically
        }

        get("/callback") {
            val principal: OAuthAccessTokenResponse.OAuth2? = call.principal()
            call.sessions.set(UserSession(principal!!.state!!, principal.accessToken))
            val redirect = redirects[principal.state!!]
            call.respondRedirect(redirect!!)
        }
    }
}