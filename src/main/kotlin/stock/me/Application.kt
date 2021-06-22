package stock.me

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.serialization.*
import stock.me.routes.registerStockRoutes

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }
    registerStockRoutes()
}
