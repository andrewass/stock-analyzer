package stockcomp.client.backend.authentication

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.sessions.*
import kotlinx.serialization.Serializable
import stockcomp.client.backend.config.HttpClient
import stockcomp.client.backend.plugins.UserSession

fun Application.configureCustomAuthentication() {
    install(RefreshTokenPlugin)

    install(Authentication) {
        oauth("auth-oauth-google") {
            urlProvider = { "http://stockcomp.io/api/auth/callback" }
            providerLookup = {
                OAuthServerSettings.OAuth2ServerSettings(
                    name = "google",
                    authorizeUrl = "https://accounts.google.com/o/oauth2/auth",
                    accessTokenUrl = "https://oauth2.googleapis.com/token",
                    requestMethod = HttpMethod.Post,
                    clientId = System.getenv("GOOGLE_CLIENT_ID"),
                    clientSecret = System.getenv("GOOGLE_CLIENT_SECRET"),
                    defaultScopes = listOf(
                        "https://www.googleapis.com/auth/userinfo.profile",
                        "https://www.googleapis.com/auth/userinfo.email",
                        "openid"
                    ),
                    extraAuthParameters = listOf(
                        Pair("access_type", "offline"),
                        Pair("prompt", "consent")
                    ),
                )
            }
            client = HttpClient.client
        }
    }
}

val RefreshTokenPlugin = createApplicationPlugin(name = "RefreshTokenPlugin") {
    onCall { call ->
        val session: UserSession? = call.sessions.get()
        if (session != null) {
            if (System.currentTimeMillis() > session.expiresAt - 60 * 1000) {
                session.refreshToken?.let {
                    val tokenResponse = refreshGoogleToken(it)
                    val newSession = UserSession(
                        accessToken = tokenResponse.id_token,
                        refreshToken = it,
                        expiresAt = System.currentTimeMillis() + (tokenResponse.expires_in * 1000)
                    )
                    call.sessions.set(newSession)
                }
            }
        }
    }
}

suspend fun refreshGoogleToken(refreshToken: String): TokenResponse {
    val response = HttpClient.client.post("https://oauth2.googleapis.com/token") {
        contentType(ContentType.Application.Json)
        setBody(
            mapOf(
                "client_id" to System.getenv("GOOGLE_CLIENT_ID"),
                "client_secret" to System.getenv("GOOGLE_CLIENT_SECRET"),
                "refresh_token" to refreshToken,
                "grant_type" to "refresh_token"
            )
        )
    }
    return if (response.status == HttpStatusCode.OK) {
        response.body<TokenResponse>()
    } else {
        throw Exception("Failed to refresh token: ${response.status}")
    }
}

@Serializable
data class TokenResponse(
    val expires_in: Int,
    val id_token: String
)