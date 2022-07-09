package com.example.routes

import com.example.models.Customer
import com.example.models.customerStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.customerRouting() {

    route("/customer") {
        get {
            when (customerStorage.isEmpty()) {
                true -> call.respondText(text = "No customer found", status = HttpStatusCode.OK)
                false -> call.respond(customerStorage)
            }
        }

        get("{id?}") {

            val id = call.parameters["id"] ?: return@get call.respondText(
                text = "Missing Id", status = HttpStatusCode.BadRequest
            )

            val customer = customerStorage.find { it.id == id } ?: return@get call.respondText(
                text = "Customer Not Found", status = HttpStatusCode.NotFound
            )

            call.respond(customer)

        }

        post {
            val customer = call.receive<Customer>()
            customerStorage.add(customer)
            call.respondText(text = "Customer created Successfully", status = HttpStatusCode.Created)

        }
        delete("{id?}") {

            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            when (customerStorage.removeIf { it.id == id }) {
                true -> call.respondText(text = " Customer Removed", status = HttpStatusCode.Accepted)
                false -> call.respondText(text = "Not Found", status = HttpStatusCode.NotFound)
            }

        }
    }

}