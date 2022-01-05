package io.supersimple.todo.util

import io.ktor.application.ApplicationCall
import io.ktor.auth.principal
import io.supersimple.todo.data.model.User

val ApplicationCall.authenticatedUser: User? get() = principal() as? User
