package io.supersimple.todo.routes

import io.ktor.application.Application
import io.supersimple.todo.routes.user.userRouting
import io.supersimple.todo.routes.notes.notesRoutes

fun Application.installAppRouting() {
    notesRoutes()
    userRouting()
}
