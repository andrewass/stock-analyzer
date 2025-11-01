package stockcomp.client.backend.consumer

import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsChannel
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import io.ktor.http.contentType
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receiveText
import io.ktor.server.response.respondBytes
import io.ktor.server.sessions.get
import io.ktor.server.sessions.sessions
import io.ktor.utils.io.toByteArray
import stockcomp.client.backend.config.HttpClient
import stockcomp.client.backend.plugins.UserSession

suspend fun ApplicationCall.forwardToResourceServer(
    httpMethod: HttpMethod,
    targetUrl: String,
) {
    val userSession = sessions.get<UserSession>() ?: error("No active user session found")

    val response =
        HttpClient.client.request(targetUrl) {
            method = httpMethod
            url {
                getParams(this@forwardToResourceServer.parameters).forEach { parameters.append(it.key, it.value) }
            }
            contentType(ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer ${userSession.accessToken}")

            if (httpMethod in listOf(HttpMethod.Put, HttpMethod.Post, HttpMethod.Patch)) {
                setBody(receiveText())
            }
        }

    respondBytes(
        bytes = response.bodyAsChannel().toByteArray(),
        status = response.status,
        contentType = response.contentType(),
    )
}

private fun getParams(parameters: Parameters): List<Parameter> =
    parameters.entries().map { Parameter(key = it.key, value = it.value.first()) }

private class Parameter(
    val key: String,
    val value: String,
)
