package stock.me

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.serialization.*
import kotlinx.coroutines.runBlocking
import stock.me.graphql.registerGraphQLSchema
import stock.me.routes.registerRoutes
import stock.me.symbols.populator.initStockTasks

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.main() = runBlocking {

    install(ContentNegotiation) {
        json()
    }

    install(CORS) {
        allowCredentials = true
        host("localhost:8000")
        host("localhost:8080")
        header(HttpHeaders.ContentType)
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Patch)
        method(HttpMethod.Delete)
    }

    registerRoutes()
    registerGraphQLSchema()
    initStockTasks()
}
