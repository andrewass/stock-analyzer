package stock.me

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.serialization.*
import kotlinx.coroutines.runBlocking
import org.kodein.di.ktor.di
import stock.me.config.bindComponents
import stock.me.routes.registerRoutes
import stock.me.task.initStockTasks

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() = runBlocking {
    install(ContentNegotiation) {
        json()
    }
    install(CORS) {
        allowCredentials = true
    }
    di {
        bindComponents()
    }
    registerRoutes()
    initStockTasks()
}
