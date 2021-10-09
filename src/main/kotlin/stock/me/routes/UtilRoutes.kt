package stock.me.routes

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*


fun Route.utilRoute(){

    route("/util"){

        get("/ping"){
            call.respond("Ping")
        }
    }
}