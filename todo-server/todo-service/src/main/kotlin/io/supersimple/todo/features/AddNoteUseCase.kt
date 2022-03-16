package io.supersimple.todo.features

import io.supersimple.auth.model.User
import io.supersimple.todo.model.Note
import io.supersimple.todo.repositories.NotesRepository
import io.supersimple.todo.route.dto.AddNoteDTO
import io.supersimple.todo.route.dto.toNote

class AddNoteUseCase(private val notesRepository: NotesRepository) {
    suspend operator fun invoke(authenticatedUser: User, note: AddNoteDTO): Note {
        val newNote = note.toNote(authenticatedUser, notesRepository.countNotes(authenticatedUser.id))
        return notesRepository.addNote(newNote)
    }
}
