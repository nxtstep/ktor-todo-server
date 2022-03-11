package io.supersimple.todo.features

import io.supersimple.auth.model.User
import io.supersimple.todo.model.Note
import io.supersimple.todo.repositories.NotesRepository

class ListPrivateNotesUseCase(private val notesRepository: NotesRepository) {
    suspend operator fun invoke(user: User): List<Note> =
        notesRepository.listNotes(user.id)
}
