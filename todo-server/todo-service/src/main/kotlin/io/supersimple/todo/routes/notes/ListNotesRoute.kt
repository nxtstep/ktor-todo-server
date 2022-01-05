package io.supersimple.todo.routes.notes

import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import io.supersimple.todo.api.error.NotFoundException
import io.supersimple.todo.controllers.AuthenticationController
import io.supersimple.todo.features.notes.ListPrivateNotesUseCase
import io.supersimple.todo.features.notes.ListPublicNotesUseCase
import io.supersimple.todo.util.authenticatedUser

const val NOTES = "/notes/{username}"

@Location(NOTES)
data class NotesRoute(val username: String)

fun Route.listNotesRoute(
    listPublicNotes: ListPublicNotesUseCase,
    listPrivateNotes: ListPrivateNotesUseCase
) {
    authenticate(AuthenticationController.authKey, optional = true) {
        get<NotesRoute> { notesRoute ->
            with(call) {
                val notes = authenticatedUser?.takeIf { user ->
                    user.name == notesRoute.username
                }?.let { user ->
                    listPrivateNotes(user)
                } ?: listPublicNotes.invoke(notesRoute.username)
                ?: throw NotFoundException("Notes not found for ${notesRoute.username}")
                respond(notes)
            }
        }
    }
}
