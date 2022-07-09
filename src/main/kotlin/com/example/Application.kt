package com.example

import com.example.data.DatabaseFactory
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*

fun main() {

    DatabaseFactory.init()

    embeddedServer(Netty, port = 8080, host = "0.0.0.0", watchPaths = listOf("classes", "resources")) {
        configureRouting()
        configureSerialization()
    }.start(wait = true)
}
