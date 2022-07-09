package com.example.routes

import com.example.data.Poems
import com.example.models.Poem
import com.example.repository.PoemRepository
import com.example.utils.Response
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.poemRouting() {

    val db = PoemRepository()

    route("poems") {
        get {
            val poems = db.getAll()

            when (poems.isEmpty()) {
                true -> call.respondText(text = "No poem found", status = HttpStatusCode.OK)
                false -> call.respond(
                    Response(
                        success = true, message = "Poems Found", data = poems
                    )
                )
            }

        }

        post {

            val poem = call.receive<Poem>()

            val some = db.create(content = poem.content, userId = poem.userId)

            call.respond(
                message = Response(success = true, message = "Poem created", data = some),
                status = HttpStatusCode.Created
            )

        }
    }

}