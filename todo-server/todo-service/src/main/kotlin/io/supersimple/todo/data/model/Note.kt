package io.supersimple.todo.data.model

import io.supersimple.common.serialization.LocalDateTimeSerializer
import io.supersimple.common.serialization.UUIDSerializer
import io.supersimple.todo.data.model.meta.ObjectVersion
import java.time.LocalDateTime
import java.util.UUID

@JvmInline
@kotlinx.serialization.Serializable
value class NoteID(
    @kotlinx.serialization.Serializable(with = UUIDSerializer::class)
    val value: UUID
)

typealias NoteTag = String

@kotlinx.serialization.Serializable
data class Note(
    val id: NoteID = NoteID(UUID.randomUUID()),
    val owner: UserID,
    val title: String,
    val body: String?,
    val public: Boolean = false,
    val sortOrder: Int,
    val tags: List<NoteTag>,
    @kotlinx.serialization.Serializable(LocalDateTimeSerializer::class)
    val created: LocalDateTime = LocalDateTime.now(),
    @kotlinx.serialization.Serializable(LocalDateTimeSerializer::class)
    val modified: LocalDateTime = LocalDateTime.now(),
    val version: ObjectVersion = ObjectVersion(1)
)
