package io.supersimple.todo.features.notes

import io.supersimple.todo.data.model.Note
import io.supersimple.todo.data.model.NoteTag
import io.supersimple.todo.data.repositories.notes.NotesRepository
import io.supersimple.todo.data.model.User
import kotlinx.serialization.Serializable

class AddNoteUseCase(private val notesRepository: NotesRepository) {
    suspend operator fun invoke(authenticatedUser: User, note: AddNoteDTO): Note =
        notesRepository.addNote(note.toNote(authenticatedUser, notesRepository.countNotes(authenticatedUser.id)))
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
