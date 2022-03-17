package io.supersimple.todo.route

import io.ktor.application.call
import io.ktor.locations.Location
import io.ktor.response.respond
import io.ktor.routing.Route
import io.supersimple.auth.extension.*
import io.supersimple.common.api.error.NotFoundException
import io.supersimple.todo.features.ListPrivateNotesUseCase
import io.supersimple.todo.features.ListPublicNotesUseCase

const val NOTES = "/notes/{username}"

@Location(NOTES)
data class NotesRoute(val username: String)

fun Route.listNotesRoute(
    listPublicNotes: ListPublicNotesUseCase,
    listPrivateNotes: ListPrivateNotesUseCase,
) {
    authenticatedGet<NotesRoute>(optional = true) { notesRoute ->

        val notes = when (authenticatedUser?.name) {
            notesRoute.username -> listPrivateNotes(authenticatedUser!!)
            else -> listPublicNotes(notesRoute.username)
        } ?: throw NotFoundException("Notes not found for ${notesRoute.username}")

        call.respond(notes)
    }
}
