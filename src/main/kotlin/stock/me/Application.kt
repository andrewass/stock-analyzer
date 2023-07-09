package stock.me


import io.ktor.server.application.*
import stock.me.plugins.configureCors
import stock.me.plugins.configureRouting
import stock.me.plugins.configureSerialization

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {

    configureSerialization()
    configureCors()
    configureRouting()
    initStockTasks()
}
