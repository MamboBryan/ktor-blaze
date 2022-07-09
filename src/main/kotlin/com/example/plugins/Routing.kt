package com.example.plugins

import com.example.routes.*
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*

fun Application.configureRouting() {

    routing {
        customerRouting()
        listOrderRoute()
        getOrderRoute()
        totalizeOrderRoute()
        poemRouting()
        get("/") {
            call.respondText("Hello World!")
        }
    }
}
