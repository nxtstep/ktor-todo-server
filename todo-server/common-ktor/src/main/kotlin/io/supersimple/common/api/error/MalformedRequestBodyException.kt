package io.supersimple.common.api.error

import io.ktor.http.HttpStatusCode

public class MalformedRequestBodyException(description: String? = null) :
    HttpError(HttpStatusCode.BadRequest, description ?: "Malformed request body")
