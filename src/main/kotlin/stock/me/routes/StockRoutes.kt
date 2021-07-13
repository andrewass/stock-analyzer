package stock.me.routes

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.elasticsearch.client.RestHighLevelClient
import org.kodein.di.instance
import org.kodein.di.ktor.di
import stock.me.service.EntitySearchService

fun Route.stockRoute() {
    val restClient by di().instance<RestHighLevelClient>()
    val entitySearchService by di().instance<EntitySearchService>()

    route("/stock") {
        get("/query/{word}") {
            val word = call.parameters["word"] ?: return@get call.respond(HttpStatusCode.BadRequest)
            val result = entitySearchService.getSuggestions(restClient, word)
            call.respond(result)
        }
    }
}
