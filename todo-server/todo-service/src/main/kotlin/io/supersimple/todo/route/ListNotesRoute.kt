package io.supersimple.todo.route

import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.application
import io.supersimple.auth.attribute.AuthenticationKey
import io.supersimple.auth.util.authenticatedUser
import io.supersimple.common.api.error.NotFoundException
import io.supersimple.todo.features.ListPrivateNotesUseCase
import io.supersimple.todo.features.ListPublicNotesUseCase

const val NOTES = "/notes/{username}"

@Location(NOTES)
data class NotesRoute(val username: String)

fun Route.listNotesRoute(
    listPublicNotes: ListPublicNotesUseCase,
    listPrivateNotes: ListPrivateNotesUseCase,
    authenticationKey: String = application.attributes[AuthenticationKey],
) {
    authenticate(authenticationKey, optional = true) {
        get<NotesRoute> { notesRoute ->
            with(call) {
                val notes = authenticatedUser?.takeIf { user ->
                    user.name == notesRoute.username
                }?.let { user ->
                    listPrivateNotes(user)
                } ?: listPublicNotes(notesRoute.username)
                ?: throw NotFoundException("Notes not found for ${notesRoute.username}")
                respond(notes)
            }
        }
    }
}
