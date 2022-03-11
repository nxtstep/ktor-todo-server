package io.supersimple.todo.attribute

import io.ktor.util.AttributeKey
import io.supersimple.todo.repositories.NotesRepository

val NotesRepositoryKey = AttributeKey<NotesRepository>("notes-repository-key")
