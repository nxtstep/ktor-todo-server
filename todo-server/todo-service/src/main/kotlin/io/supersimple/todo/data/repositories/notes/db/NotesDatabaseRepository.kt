package io.supersimple.todo.data.repositories.notes.db

import io.supersimple.todo.data.model.Note
import io.supersimple.todo.data.model.NoteID
import io.supersimple.todo.data.model.NoteTag
import io.supersimple.todo.data.model.meta.ObjectVersion
import io.supersimple.todo.data.repositories.notes.NotesRepository
import io.supersimple.todo.data.repositories.notes.db.NotesEntity.body
import io.supersimple.todo.data.repositories.notes.db.NotesEntity.created
import io.supersimple.todo.data.repositories.notes.db.NotesEntity.id
import io.supersimple.todo.data.repositories.notes.db.NotesEntity.modified
import io.supersimple.todo.data.repositories.notes.db.NotesEntity.ownerId
import io.supersimple.todo.data.repositories.notes.db.NotesEntity.public
import io.supersimple.todo.data.repositories.notes.db.NotesEntity.sortOrder
import io.supersimple.todo.data.repositories.notes.db.NotesEntity.title
import io.supersimple.todo.data.repositories.notes.db.NotesEntity.version
import io.supersimple.todo.data.model.UserID
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.count
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.update
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.UUID
import org.joda.time.DateTime as JodaDateTime

class NotesDatabaseRepository(private val database: NotesDatabase) : NotesRepository {
    override suspend fun listNotes(userId: UserID): List<Note> = database.execTransaction {
        NotesEntity.select { ownerId eq userId.value }
            .orderBy(sortOrder)
            .map { row ->
                row.toNote {
                    TagEntity.select { TagEntity.note eq row[id] }.map { tagResult ->
                        tagResult[TagEntity.tag]
                    }
                }
            }
    }

    override suspend fun listPublicNotes(userId: UserID): List<Note> {
        TODO("Not yet implemented")
    }

    override suspend fun get(noteID: NoteID): Note? {
        TODO("Not yet implemented")
    }

    override suspend fun addNote(note: Note): Note =
        database.execTransaction {
            NotesEntity.insert(note.toEntity())
            note.tags.forEach { value -> // TODO extract
                TagEntity.insert {
                    it[tag] = value
                    it[TagEntity.note] = note.id.value
                }
            }

            note
        }

    override suspend fun updateNote(note: Note): Note {
        NotesEntity.update(where = { id eq note.id.value }) { note.toEntity() }
        TODO("Not yet implemented")
    }

    override suspend fun deleteNote(noteID: NoteID) {
        TODO("Not yet implemented")
    }

    override suspend fun countNotes(userId: UserID): Int = database.execTransaction {
        NotesEntity.slice(id.count()).select { ownerId eq userId.value }.map {
            it[id.count()]
        }.firstOrNull() ?: 0
    }
}

private fun Note.toEntity(): NotesEntity.(InsertStatement<Number>) -> Unit = {
    with(this@toEntity) {
        it[NotesEntity.id] = id.value
        it[NotesEntity.ownerId] = owner.value
        it[NotesEntity.title] = title
        it[NotesEntity.body] = body
        it[NotesEntity.public] = public
        it[NotesEntity.sortOrder] = sortOrder
        it[NotesEntity.created] = JodaDateTime(created.toEpochSecond(ZoneOffset.UTC) * 1_000)
        it[NotesEntity.modified] = JodaDateTime(modified.toEpochSecond(ZoneOffset.UTC) * 1_000)
        it[NotesEntity.version] = version.value
    }
}


private fun ResultRow.toNote(tagsFetcher: (UUID) -> List<NoteTag>): Note = let { row ->
    Note(
        id = NoteID(row[id]),
        owner = UserID(row[ownerId]),
        title = row[title],
        body = row[body],
        public = row[public],
        sortOrder = row[sortOrder],
        tags = tagsFetcher(row[id]),
        created = LocalDateTime.ofEpochSecond(row[created].toDate().time / 1_000, 0, ZoneOffset.UTC),
        modified = LocalDateTime.ofEpochSecond(row[modified].toDate().time / 1_000, 0, ZoneOffset.UTC),
        version = ObjectVersion(row[version])
    )
}
