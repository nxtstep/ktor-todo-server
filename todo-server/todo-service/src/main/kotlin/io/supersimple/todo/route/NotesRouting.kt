package io.supersimple.todo.route

import io.ktor.application.Application
import io.ktor.routing.routing
import io.supersimple.auth.repository.UserRepository
import io.supersimple.todo.features.AddNoteUseCase
import io.supersimple.todo.features.ListPrivateNotesUseCase
import io.supersimple.todo.features.ListPublicNotesUseCase
import io.supersimple.todo.repositories.NotesRepository

fun Application.notesRouting(notesRepository: NotesRepository, userRepository: UserRepository) {
    val addNoteUseCase = AddNoteUseCase(notesRepository)
    val listPublicNotes = ListPublicNotesUseCase(notesRepository, userRepository)
    val listPrivateNotes = ListPrivateNotesUseCase(notesRepository)

    routing {
        addNoteRoute(addNoteUseCase)
        listNotesRoute(listPublicNotes, listPrivateNotes)
    }
}
