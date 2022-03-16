package io.supersimple.todo.route.dto

import io.supersimple.auth.model.User
import io.supersimple.todo.model.Note
import io.supersimple.todo.model.NoteTag
import kotlinx.serialization.Serializable

@Serializable
data class AddNoteDTO(
    val title: String,
    val body: String? = null,
    val tags: Set<NoteTag> = emptySet(),
)

fun AddNoteDTO.toNote(owner: User, sortOrder: Int): Note = Note(
    owner = owner.id,
    title = title,
    body = body,
    tags = tags,
    sortOrder = sortOrder
)
