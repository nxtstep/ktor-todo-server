package io.supersimple.todo.mysql

import io.ktor.application.Application
import io.supersimple.auth.attribute.UserRepositoryKey
import io.supersimple.db.SQLDatabase
import io.supersimple.db.mysql.MySQLDatabase
import io.supersimple.todo.attribute.NotesRepositoryKey
import io.supersimple.todo.repositories.db.NotesDatabase
import io.supersimple.todo.repositories.db.NotesDatabaseRepository
import io.supersimple.todo.repositories.db.NotesEntity
import io.supersimple.todo.repositories.db.TagEntity
import io.supersimple.todo.route.notesRouting
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger

fun Application.module() {
    val notesRepository = NotesDatabaseRepository(NotesDatabase(createNotesDatabase()))
    attributes.put(NotesRepositoryKey, notesRepository)
    notesRouting(notesRepository, attributes[UserRepositoryKey])
}

fun Application.createNotesDatabase(): SQLDatabase = with(environment) {
    val username = config.property("db.username").getString()
    val password = config.property("db.password").getString()
    val host = config.property("db.host").getString()
    val database = config.property("db.database").getString()
    val port = config.property("db.port").getString().toInt()

    return MySQLDatabase(host = host, port = port, database = database, user = username, passwd = password) {
        addLogger(StdOutSqlLogger)
        SchemaUtils.create(NotesEntity, TagEntity, inBatch = true)
    }
}

