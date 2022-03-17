package io.supersimple.auth.api.error

import io.ktor.http.HttpStatusCode
import io.supersimple.common.api.error.HttpError

public class AuthenticationException(description: String? = null) :
    HttpError(HttpStatusCode.Unauthorized, description ?: "Unauthorized")
