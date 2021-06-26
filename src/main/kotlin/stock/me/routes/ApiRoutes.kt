package stock.me.routes

import io.ktor.routing.*

fun Routing.apiRoute(){
    route("/api"){
        stockRoute()
    }
}