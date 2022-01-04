package io.supersimple.todo

import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.supersimple.todo.plugins.configureRouting

fun main() {
    embeddedServer(Netty, port = 8081, host = "0.0.0.0") {
        configureRouting()
    }.start(wait = true)
}