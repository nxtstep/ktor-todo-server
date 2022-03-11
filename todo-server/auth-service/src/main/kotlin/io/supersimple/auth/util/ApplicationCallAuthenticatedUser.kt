package io.supersimple.auth.util

import io.ktor.application.ApplicationCall
import io.ktor.auth.principal
import io.supersimple.auth.model.User

val ApplicationCall.authenticatedUser: User? get() = principal() as? User
