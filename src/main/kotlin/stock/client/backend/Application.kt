package stock.client.backend


import io.ktor.server.application.*
import stock.client.backend.plugins.configureCors
import stock.client.backend.plugins.configureRouting
import stock.client.backend.plugins.configureSerialization
import stock.client.backend.symbols.populate.initStockTasks

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    configureSerialization()
    configureCors()
    configureRouting()
    initStockTasks()
}
