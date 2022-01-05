package io.supersimple.todo.api.error

import io.ktor.http.HttpStatusCode

class NotFoundException(description: String?) : HttpError(HttpStatusCode.NotFound, description ?: "Resource not found")
