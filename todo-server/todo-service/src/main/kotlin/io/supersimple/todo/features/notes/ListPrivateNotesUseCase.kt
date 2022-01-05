package io.supersimple.todo.features.notes

import io.supersimple.todo.data.model.Note
import io.supersimple.todo.data.repositories.notes.NotesRepository
import io.supersimple.todo.data.model.User

class ListPrivateNotesUseCase(private val notesRepository: NotesRepository) {
    suspend operator fun invoke(user: User): List<Note> =
        notesRepository.listNotes(user.id)
}
