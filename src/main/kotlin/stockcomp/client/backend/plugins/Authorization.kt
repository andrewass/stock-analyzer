package stockcomp.client.backend.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import stockcomp.client.backend.config.HttpClient

fun Application.configureAuthorization() {

    install(Authentication) {
        oauth("auth-oauth-google") {
            urlProvider = { "http://localhost:8088/callback" }
            providerLookup = {
                OAuthServerSettings.OAuth2ServerSettings(
                    name = "google",
                    authorizeUrl = "https://accounts.google.com/o/oauth2/auth",
                    accessTokenUrl = "https://accounts.google.com/o/oauth2/token",
                    requestMethod = HttpMethod.Post,
                    clientId = System.getenv("GOOGLE_CLIENT_ID"),
                    clientSecret = System.getenv("GOOGLE_CLIENT_SECRET"),
                    defaultScopes = listOf("https://www.googleapis.com/auth/userinfo.profile"),
                    extraAuthParameters = listOf("access_type" to "offline"),
                )
            }
            client = HttpClient.client
        }
    }
}