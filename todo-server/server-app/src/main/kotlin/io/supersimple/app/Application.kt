package io.supersimple.app

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.Compression
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.features.StatusPages
import io.ktor.features.deflate
import io.ktor.features.gzip
import io.ktor.features.minimumSize
import io.ktor.http.HttpStatusCode
import io.ktor.locations.Locations
import io.ktor.response.respond
import io.ktor.serialization.json
import io.supersimple.common.api.error.HttpError
import kotlinx.serialization.json.Json
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
}
