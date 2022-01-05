package io.supersimple.todo

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.jwt.jwt
import io.ktor.features.CallLogging
import io.ktor.features.Compression
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.features.StatusPages
import io.ktor.features.deflate
import io.ktor.features.gzip
import io.ktor.features.minimumSize
import io.ktor.locations.Locations
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.serialization.json
import io.supersimple.db.SQLDatabase
import io.supersimple.db.mysql.MySQLDatabase
import io.supersimple.todo.api.error.HttpError
import io.supersimple.todo.controllers.AuthenticationController
import io.supersimple.todo.controllers.AuthenticationController.Companion.authKey
import io.supersimple.todo.data.repositories.notes.db.NotesDatabase
import io.supersimple.todo.data.repositories.notes.db.NotesEntity
import io.supersimple.todo.data.repositories.notes.db.TagEntity
import io.supersimple.todo.routes.installAppRouting
import io.supersimple.todo.routes.user.createUserModule
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.kodein.di.bind
import org.kodein.di.ktor.di
import org.kodein.di.singleton
import org.slf4j.event.Level

fun Application.module() {
    install(DefaultHeaders)
    install(CallLogging) {
        level = Level.INFO
    }
    install(Locations)
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }
    install(Compression) {
        gzip {
            priority = 1.0
        }
        deflate {
            priority = 10.0
            minimumSize(1024) // condition
        }
    }
    di {
        import(createUserModule()) // FIXME remove, still here because of the AuthenticationController
        bind<NotesDatabase>() with singleton { NotesDatabase(createApplicationDatabase()) }
    }

    install(StatusPages) {
        exception<RuntimeException> { cause ->
            call.respond(HttpStatusCode.InternalServerError, "Get outta herr")
            throw cause
        }
        exception<HttpError> { cause ->
            call.respond(cause.statusCode, cause.error)
            throw cause
        }
        exception<Throwable> { cause ->
            call.respond(cause.message ?: "Unknown error")
            throw cause
        }
    }
    install(Authentication) {
        val controller = AuthenticationController()
        jwt(authKey, controller::configure)
    }

    installAppRouting()

}

fun Application.createApplicationDatabase(): SQLDatabase = with(environment) {
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
