package io.supersimple.todo.routes.notes

import io.ktor.application.call
import io.ktor.locations.Location
import io.ktor.response.respond
import io.ktor.routing.Route
import io.supersimple.common.route.authenticatedPost
import io.supersimple.common.serialization.safeReceiveOrNull
import io.supersimple.todo.api.error.AuthenticationException
import io.supersimple.todo.api.error.MalformedRequestBodyException
import io.supersimple.todo.controllers.AuthenticationController.Companion.authKey
import io.supersimple.todo.features.notes.AddNoteDTO
import io.supersimple.todo.features.notes.AddNoteUseCase
import io.supersimple.todo.util.authenticatedUser

const val ADD_NOTE = "/notes"

@Location(ADD_NOTE)
object AddNoteRoute

fun Route.addNoteRoute(addNote: AddNoteUseCase) {
    authenticatedPost<AddNoteRoute>(authKey) {
        with(call) {
            val authenticatedUser = authenticatedUser
                ?: throw AuthenticationException("Cannot create notes for unauthenticated users.")
            val note = call.safeReceiveOrNull<AddNoteDTO>() ?: throw MalformedRequestBodyException()
            respond(addNote(authenticatedUser, note))
        }
    }
}
