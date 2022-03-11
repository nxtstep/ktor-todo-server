package io.supersimple.todo.route

import io.ktor.application.call
import io.ktor.locations.Location
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.application
import io.supersimple.auth.attribute.AuthenticationKey
import io.supersimple.auth.util.authenticatedUser
import io.supersimple.common.api.error.AuthenticationException
import io.supersimple.common.api.error.MalformedRequestBodyException
import io.supersimple.common.extension.authenticatedPost
import io.supersimple.common.serialization.safeReceiveOrNull
import io.supersimple.todo.features.AddNoteDTO
import io.supersimple.todo.features.AddNoteUseCase

const val ADD_NOTE = "/notes"

@Location(ADD_NOTE)
object AddNoteRoute

fun Route.addNoteRoute(addNote: AddNoteUseCase, authenticationKey: String = application.attributes[AuthenticationKey]) {
    authenticatedPost<AddNoteRoute>(authenticationKey) {
        with(call) {
            val authenticatedUser = authenticatedUser
                ?: throw AuthenticationException("Cannot create notes for unauthenticated users.")
            val note = call.safeReceiveOrNull<AddNoteDTO>() ?: throw MalformedRequestBodyException()
            respond(addNote(authenticatedUser, note))
        }
    }
}
