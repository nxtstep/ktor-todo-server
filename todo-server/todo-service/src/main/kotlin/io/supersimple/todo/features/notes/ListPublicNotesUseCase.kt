package io.supersimple.todo.features.notes

import io.supersimple.todo.data.model.Note
import io.supersimple.todo.data.repositories.notes.NotesRepository
import io.supersimple.todo.data.repositories.users.UserRepository

class ListPublicNotesUseCase(private val notesRepository: NotesRepository, private val userRepository: UserRepository) {
    suspend operator fun invoke(username: String): List<Note>? =
        userRepository.findUser(username)?.let { user ->
            notesRepository.listPublicNotes(user.id)
        }
}
