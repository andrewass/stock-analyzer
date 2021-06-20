package stock.me

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import stock.me.routes.registerStockRoutes

fun main() {
    embeddedServer(Netty, port = 8088, host = "127.0.0.1") {
        install(ContentNegotiation) {
            json()
        }
        registerStockRoutes()
    }.start(wait = true)
}
