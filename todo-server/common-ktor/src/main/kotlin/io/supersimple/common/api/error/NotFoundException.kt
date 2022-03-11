package io.supersimple.common.api.error

import io.ktor.http.HttpStatusCode

public class NotFoundException(description: String?) : HttpError(HttpStatusCode.NotFound, description ?: "Resource not found")
