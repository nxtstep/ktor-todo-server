package io.supersimple.todo.api.error

import io.ktor.http.HttpStatusCode

class LoginException: HttpError(HttpStatusCode.Unauthorized, "Unknown username and password combination")