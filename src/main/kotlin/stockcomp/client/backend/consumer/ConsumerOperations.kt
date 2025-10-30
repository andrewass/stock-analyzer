package stockcomp.client.backend.consumer

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.sessions.*
import stockcomp.client.backend.config.HttpClient
import stockcomp.client.backend.plugins.UserSession

suspend fun callResourceServerGet(
    call: ApplicationCall,
    serverUrl: String,
) {
    val userSession: UserSession? = call.sessions.get()
    val response =
        HttpClient.client.get(serverUrl) {
            url {
                getParams(call.parameters).forEach { parameters.append(it.key, it.value) }
            }
            contentType(ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer ${userSession!!.accessToken}")
            setBody(call.receiveText())
        }
    handleResponse(response, call)
}

suspend fun callResourceServerPost(
    call: ApplicationCall,
    serverUrl: String,
) {
    val userSession: UserSession? = call.sessions.get()
    val response =
        HttpClient.client.post(serverUrl) {
            url {
                getParams(call.parameters).forEach { parameters.append(it.key, it.value) }
            }
            contentType(ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer ${userSession!!.accessToken}")
            setBody(call.receiveText())
        }
    handleResponse(response, call)
}

suspend fun callResourceServerPatch(
    call: ApplicationCall,
    serverUrl: String,
) {
    val userSession: UserSession? = call.sessions.get()
    val response =
        HttpClient.client.patch(serverUrl) {
            url {
                getParams(call.parameters).forEach { parameters.append(it.key, it.value) }
            }
            contentType(ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer ${userSession!!.accessToken}")
            setBody(call.receiveText())
        }
    handleResponse(response, call)
}

suspend fun callResourceServerDelete(
    call: ApplicationCall,
    serverUrl: String,
) {
    val userSession: UserSession? = call.sessions.get()
    val response =
        HttpClient.client.delete(serverUrl) {
            url {
                getParams(call.parameters).forEach { parameters.append(it.key, it.value) }
            }
            contentType(ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer ${userSession!!.accessToken}")
            setBody(call.receiveText())
        }
    handleResponse(response, call)
}

private suspend fun handleResponse(
    response: HttpResponse,
    call: ApplicationCall,
) {
    when (response.status) {
        HttpStatusCode.NoContent -> call.response.status(HttpStatusCode.NoContent)
        HttpStatusCode.OK -> {
            val responseBody = response.bodyAsText()
            call.respondText(responseBody, ContentType.Application.Json)
        }
        else -> {
            val error = response.bodyAsText()
            call.respond(response.status, error)
        }
    }
}

private fun getParams(parameters: Parameters): List<Parameter> =
    parameters.entries().map { Parameter(key = it.key, value = it.value.first()) }

private class Parameter(
    val key: String,
    val value: String,
)
