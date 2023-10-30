package stockcomp.client.backend.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import stockcomp.client.backend.config.HttpClient

fun Application.configureCustomAuthentication() {
    install(Authentication) {
        oauth("custom-oauth2") {
            urlProvider = { "http://stockcompclient.io/api/callback" }
            providerLookup = {
                OAuthServerSettings.OAuth2ServerSettings(
                    name = "custom-oauth2",
                    authorizeUrl = "http://authfrontend.io/authorize",
                    accessTokenUrl = "http://auth-backend-service:8089/token",
                    requestMethod = HttpMethod.Post,
                    clientId = System.getenv("CUSTOM_CLIENT_ID"),
                    clientSecret = System.getenv("CUSTOM_CLIENT_SECRET")
                )
            }
            client = HttpClient.client
        }
    }
}