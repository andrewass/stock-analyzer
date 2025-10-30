package stockcomp.client.backend.authentication

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
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
