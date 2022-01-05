package io.supersimple.todo.routes.notes

import io.ktor.application.Application
import io.ktor.routing.routing
import org.kodein.di.instance
import org.kodein.di.ktor.controller.controller

fun Application.notesRoutes() {
    routing {
        controller {
            NotesController(instance())
        }
    }
}
