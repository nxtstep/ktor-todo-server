package io.supersimple.todo.api.error

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable

open class HttpError constructor(
    val statusCode: HttpStatusCode,
    val error: HttpErrorMessage,
) : Exception() {
    constructor(statusCode: HttpStatusCode, error: String) : this(statusCode, listOf(error))
    constructor(statusCode: HttpStatusCode, error: List<String>) :
        this(
            statusCode, StringHttpErrorMessage(
                error.joinToString(separator = ",", prefix = "[", postfix = "]")
            ) // TODO (json-safe) escape original when needed - map { it.escapeIfNeeded() }
        )

    override val message: String?
        get() = error.body?.toString()
}

interface HttpErrorMessage {
    val body: Any?
}

@Serializable
data class StringHttpErrorMessage(override val body: String) : HttpErrorMessage