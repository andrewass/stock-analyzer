package stockcomp.client.backend.plugins

import io.ktor.server.application.*
import io.ktor.server.sessions.*
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
