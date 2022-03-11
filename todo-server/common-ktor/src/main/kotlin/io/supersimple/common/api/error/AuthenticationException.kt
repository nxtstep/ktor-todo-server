package io.supersimple.common.api.error

import io.ktor.http.HttpStatusCode

public class AuthenticationException(description: String? = null) :
    HttpError(HttpStatusCode.Unauthorized, description ?: "Unauthorized")
