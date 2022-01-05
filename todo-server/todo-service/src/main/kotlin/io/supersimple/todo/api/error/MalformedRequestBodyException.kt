package io.supersimple.todo.api.error

import io.ktor.http.HttpStatusCode

class MalformedRequestBodyException(description: String? = null) :
    HttpError(HttpStatusCode.BadRequest, description ?: "Malformed request body")