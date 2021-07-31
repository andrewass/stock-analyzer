package stock.me

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.serialization.*
import kotlinx.coroutines.runBlocking
import org.kodein.di.ktor.di
import stock.me.config.bindComponents
import stock.me.config.initDatabase
import stock.me.routes.registerRoutes
import stock.me.task.initStockTasks

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() = runBlocking {
    initDatabase()
    install(ContentNegotiation) {
        json()
    }
    install(CORS){
        method(HttpMethod.Get)
        allowCredentials = true
        anyHost()
    }
    di {
        bindComponents()
    }
    registerRoutes()
    initStockTasks()
}
