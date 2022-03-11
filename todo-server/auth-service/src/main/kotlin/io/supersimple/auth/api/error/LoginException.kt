package io.supersimple.auth.api.error

import io.ktor.http.HttpStatusCode
import io.supersimple.common.api.error.HttpError

class LoginException: HttpError(HttpStatusCode.Unauthorized, "Unknown username and password combination")
