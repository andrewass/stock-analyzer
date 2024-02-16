package stockcomp.client.backend.consumer

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.sessions.*
import stockcomp.client.backend.config.HttpClient
import stockcomp.client.backend.plugins.UserSession


suspend fun callResourceServerGet(call: ApplicationCall, serverUrl: String, ) {
    val userSession: UserSession? = call.sessions.get()
    val response = HttpClient.client.get(serverUrl) {
        url {
            getParams(call.parameters).forEach { parameters.append(it.key, it.value) }
        }
        contentType(ContentType.Application.Json)
        header(HttpHeaders.Authorization, "Bearer ${userSession!!.accessToken}")
        setBody(call.receiveText())
    }
    call.respondText(response.body())
}

private fun getParams(parameters: Parameters) : List<Parameter> =
    parameters.entries().map { Parameter(key = it.key, value = it.value.first()) }

private class Parameter(
    val key: String,
    val value: String
)