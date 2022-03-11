package io.supersimple.todo.features

import io.supersimple.auth.repository.UserRepository
import io.supersimple.todo.model.Note
import io.supersimple.todo.repositories.NotesRepository

class ListPublicNotesUseCase(private val notesRepository: NotesRepository, private val userRepository: UserRepository) {
    suspend operator fun invoke(username: String): List<Note>? =
        userRepository.findUser(username)?.let { user ->
            notesRepository.listPublicNotes(user.id)
        }
}
