package io.supersimple.todo.api.error

import io.ktor.http.HttpStatusCode

class AuthenticationException(description: String? = null) :
    HttpError(HttpStatusCode.Unauthorized, description ?: "Unauthorized")
