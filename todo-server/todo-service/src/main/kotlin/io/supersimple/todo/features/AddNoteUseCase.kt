package io.supersimple.todo.features

import io.supersimple.auth.model.User
import io.supersimple.todo.model.Note
import io.supersimple.todo.model.NoteTag
import io.supersimple.todo.repositories.NotesRepository
import kotlinx.serialization.Serializable

class AddNoteUseCase(private val notesRepository: NotesRepository) {
    suspend operator fun invoke(authenticatedUser: User, note: AddNoteDTO): Note {
        val newNote = note.toNote(authenticatedUser, notesRepository.countNotes(authenticatedUser.id))
        return notesRepository.addNote(newNote)
    }
}

@Serializable
data class AddNoteDTO(
    val title: String,
    val body: String? = null,
    val tags: List<NoteTag> = emptyList(),
)

fun AddNoteDTO.toNote(owner: User, sortOrder: Int): Note = Note(
    owner = owner.id,
    title = title,
    body = body,
    tags = tags,
    sortOrder = sortOrder
)
