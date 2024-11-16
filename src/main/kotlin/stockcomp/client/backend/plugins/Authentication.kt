package stockcomp.client.backend.plugins

import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.util.*
import stockcomp.client.backend.config.HttpClient

fun Application.configureCustomAuthentication() {
    val baseUrl = environment.config.propertyOrNull("auth-server.service")?.getString()

    install(Authentication) {
        oauth("custom-oauth") {
            urlProvider = { "http://stockcomp.io/api/auth/callback" }
            providerLookup = {
                OAuthServerSettings.OAuth2ServerSettings(
                    name = "custom-oauth",
                    authorizeUrl = "http://authserver.io/authentication",
                    accessTokenUrl = "$baseUrl/server/token/token",
                    requestMethod = HttpMethod.Post,
                    clientId = System.getenv("CUSTOM_CLIENT_ID"),
                    clientSecret = System.getenv("CUSTOM_CLIENT_SECRET")
                )
            }
            client = HttpClient.client
        }

        oauth("auth-oauth-google") {
            urlProvider = { "http://stockcomp.io/api/auth/callback" }
            providerLookup = {
                OAuthServerSettings.OAuth2ServerSettings(
                    name = "google",
                    authorizeUrl = "https://accounts.google.com/o/oauth2/auth",
                    accessTokenUrl = "https://accounts.google.com/o/oauth2/token",
                    clientId = System.getenv("GOOGLE_CLIENT_ID"),
                    clientSecret = System.getenv("GOOGLE_CLIENT_SECRET"),
                    defaultScopes = listOf("https://www.googleapis.com/auth/userinfo.profile"),                )
            }
            client = HttpClient.client
        }
    }
}

class RenewExpiredToken {
    companion object Plugin : BaseApplicationPlugin<ApplicationCallPipeline, Configuration, RenewExpiredToken> {
        override val key = AttributeKey<RenewExpiredToken>("RenewExpiredToken")

        override fun install(
            pipeline: ApplicationCallPipeline,
            configure: Configuration.() -> Unit
        ): RenewExpiredToken {
            val plugin = RenewExpiredToken()
            return plugin
        }
    }
}