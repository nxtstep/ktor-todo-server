package io.supersimple.todo.route

import io.ktor.application.call
import io.ktor.locations.Location
import io.ktor.response.respond
import io.ktor.routing.Route
import io.supersimple.auth.extension.*
import io.supersimple.common.api.error.MalformedRequestBodyException
import io.supersimple.common.serialization.safeReceiveOrNull
import io.supersimple.todo.features.AddNoteUseCase
import io.supersimple.todo.route.dto.AddNoteDTO

const val ADD_NOTE = "/notes"

@Location(ADD_NOTE)
object AddNoteRoute

fun Route.addNoteRoute(addNote: AddNoteUseCase) {
    authenticatedPost<AddNoteRoute> {
        val note = call.safeReceiveOrNull<AddNoteDTO>() ?: throw MalformedRequestBodyException()
        call.respond(addNote(authenticatedUser!!, note))
    }
}
