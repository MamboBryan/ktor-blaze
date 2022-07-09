package com.example.routes

import com.example.models.Customer
import com.example.models.customerStorage
import com.example.repository.CustomerRepository
import com.example.repository.PoemRepository
import com.example.utils.Response
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.customerRouting() {

    val customerRepository = CustomerRepository()
    val poemRepository = PoemRepository()

    route("/customer") {
        route("{id?}") {
            get {
                val id = call.parameters["id"] ?: return@get call.respondText(
                    text = "Missing Id", status = HttpStatusCode.BadRequest
                )

                val customer = customerRepository.get(id = id.toInt()) ?: return@get call.respondText(
                    text = "Customer Not Found", status = HttpStatusCode.NotFound
                )

                call.respond(customer)
            }

            get("poems") {
                val id = call.parameters["id"] ?: return@get call.respondText(
                    text = "Missing Id", status = HttpStatusCode.BadRequest
                )

                val poems = poemRepository.getAllCustomerPoems(id.toInt())

                when (poems.isEmpty()) {
                    true -> call.respondText(text = "No poem found", status = HttpStatusCode.OK)
                    false -> call.respond(
                        Response(
                            success = true, message = "Poems Found", data = poems
                        )
                    )
                }
            }
        }

        get {

            val customers = customerRepository.getAll()

            when (customers.isEmpty()) {
                true -> call.respondText(text = "No customer found", status = HttpStatusCode.OK)
                false -> call.respond(
                    Response(
                        success = true, message = "Customers Found", data = customers
                    )
                )
            }
        }
        post {
            val customer = call.receive<Customer>()
            customerRepository.create(customer.firstName, customer.lastName, customer.email)
                ?: return@post call.respondText(
                    text = "Failed Creating customer", status = HttpStatusCode.PartialContent
                )

            call.respondText(text = "Customer created Successfully", status = HttpStatusCode.Created)

        }
        delete("{id?}") {

            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            when (customerStorage.removeIf { it.id == id.toInt() }) {
                true -> call.respondText(text = " Customer Removed", status = HttpStatusCode.Accepted)
                false -> call.respondText(text = "Not Found", status = HttpStatusCode.NotFound)
            }

        }
    }

}