package stockcomp.client.backend.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.sessions.Sessions
import io.ktor.server.sessions.cookie
import kotlinx.serialization.Serializable

fun Application.configureSessions() {
    install(Sessions) {
        cookie<UserSession>("AUTH_SESSION")
    }
}

@Serializable
data class UserSession(
    val accessToken: String,
    val refreshToken: String?,
    val expiresAt: Long,
)
