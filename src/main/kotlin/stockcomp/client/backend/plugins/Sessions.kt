package stockcomp.client.backend.plugins

import io.ktor.server.application.*
import io.ktor.server.sessions.*

fun Application.configureSessions() {
    install(Sessions) {
        cookie<UserSession>("AUTH_SESSION")
    }
}

data class UserSession(val state: String, val token: String)