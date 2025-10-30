package stockcomp.client.backend.authentication

import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.OAuthAccessTokenResponse
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.authentication
import io.ktor.server.response.respond
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.sessions.clear
import io.ktor.server.sessions.get
import io.ktor.server.sessions.sessions
import io.ktor.server.sessions.set
import kotlinx.serialization.Serializable
import stockcomp.client.backend.plugins.UserSession

fun Route.customAuthRoutes() {
    route("/auth") {
        authenticate("auth-oauth-google") {
            get("/login") {}

            get("/callback") {
                val principal: OAuthAccessTokenResponse.OAuth2? = call.authentication.principal()
                if (principal != null) {
                    call.sessions.set(
                        UserSession(
                            accessToken = principal.extraParameters["id_token"]!!,
                            refreshToken = principal.extraParameters["refresh_token"],
                            expiresAt = System.currentTimeMillis() + (principal.expiresIn * 1000),
                        ),
                    )
                }
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

@Serializable
data class ValidSession(
    val validSession: Boolean,
)
