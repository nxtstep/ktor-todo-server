package io.supersimple.todo.data.repositories.notes.db

import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object NotesEntity : Table(name = "NOTES") {
    val id = uuid("id").primaryKey()
    val ownerId = uuid("owner_id").index()
    val title = varchar("title", length = 500)
    val body = varchar("body", length = 5_000).nullable()
    val public = bool("public")
    val sortOrder = integer("sort_order").index()
    val created = datetime("created")
    val modified = datetime("modified")
    val version = integer(name = "object_version")
}

object TagEntity : IntIdTable(name = "TAGS") {
    val tag = varchar(name = "tag", length = 200).index()
    val note = reference("note", NotesEntity.id, onDelete = ReferenceOption.CASCADE)
}
