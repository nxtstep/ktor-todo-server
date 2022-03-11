package io.supersimple.todo.repositories

import io.supersimple.auth.model.UserID
import io.supersimple.todo.model.Note
import io.supersimple.todo.model.NoteID

interface NotesRepository {
    suspend fun listNotes(userId: UserID): List<Note>
    suspend fun listPublicNotes(userId: UserID): List<Note>
    suspend fun get(noteID: NoteID): Note?
    suspend fun addNote(note: Note): Note
    suspend fun updateNote(note: Note): Note
    suspend fun deleteNote(noteID: NoteID)
    suspend fun countNotes(userId: UserID): Int
}
