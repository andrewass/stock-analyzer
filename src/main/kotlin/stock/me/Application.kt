package stock.me


import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import kotlinx.coroutines.runBlocking
import stock.me.routes.registerRoutes
import stock.me.symbols.populate.initStockTasks

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.main() = runBlocking {

    install(ContentNegotiation) {
        jackson{
            registerModule(JavaTimeModule())
        }
    }

    install(CORS) {
        allowCredentials = true
        allowHost("localhost:8000")
        allowHost("localhost:8080")
        allowHeader(HttpHeaders.ContentType)
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Patch)
        allowMethod(HttpMethod.Delete)
    }

    registerRoutes()
    initStockTasks()
}
