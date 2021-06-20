package stock.me

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import stock.me.plugins.*

fun main() {
    embeddedServer(Netty, port = 8088, host = "127.0.0.1") {
        configureRouting()
    }.start(wait = true)
}
