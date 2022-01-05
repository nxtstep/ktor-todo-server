package io.supersimple.todo.data.repositories.notes

import io.supersimple.todo.data.model.Note
import io.supersimple.todo.data.model.NoteID
import io.supersimple.todo.data.model.UserID

interface NotesRepository {
    suspend fun listNotes(userId: UserID): List<Note>
    suspend fun listPublicNotes(userId: UserID): List<Note>
    suspend fun get(noteID: NoteID): Note?
    suspend fun addNote(note: Note): Note
    suspend fun updateNote(note: Note): Note
    suspend fun deleteNote(noteID: NoteID)
    suspend fun countNotes(userId: UserID): Int
}
