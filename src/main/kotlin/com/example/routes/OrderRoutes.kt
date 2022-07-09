package com.example.routes

import com.example.models.orderStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.listOrderRoute() {
    get("/orders") {
        // FIXME: 7/9/22 change this method
        call.respond(orderStorage).takeIf { orderStorage.isNotEmpty() }
    }
}

fun Route.getOrderRoute() {
    get("/orders/{id?}") {
        val id = call.parameters["id"] ?: return@get call.respondText(
            text = "Missing Id", status = HttpStatusCode.BadRequest
        )

        val order = orderStorage.find { it.number == id } ?: return@get call.respondText(
            text = "Not Found", status = HttpStatusCode.NotFound
        )

        call.respond(order)

    }
}

fun Route.totalizeOrderRoute() {
    get("/orders/{id?}/total") {
        val id = call.parameters["id"] ?: return@get call.respondText(
            text = "Missing Id", status = HttpStatusCode.BadRequest
        )

        val order = orderStorage.find { it.number == id } ?: return@get call.respondText(
            text = "Not Found", status = HttpStatusCode.NotFound
        )

        val total = order.contents.sumOf { it.price * it.count }
        call.respond(total)

    }
}