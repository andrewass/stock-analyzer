package stockcomp.client.backend


import io.ktor.server.application.*
import stockcomp.client.backend.plugins.configureCors
import stockcomp.client.backend.plugins.configureRouting
import stockcomp.client.backend.plugins.configureSerialization
import stockcomp.client.backend.symbols.populate.initStockTasks

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    configureSerialization()
    configureCors()
    configureRouting()
    initStockTasks()
}
