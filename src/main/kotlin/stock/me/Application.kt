package stock.me

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.routing.*
import io.ktor.serialization.*
import org.kodein.di.ktor.di
import stock.me.config.initDatabase
import stock.me.routes.apiRoute
import stock.me.service.bindServices

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    initDatabase()

    install(ContentNegotiation) {
        json()
    }

    di{
        bindServices()
    }

    routing {
        apiRoute()
    }
}
