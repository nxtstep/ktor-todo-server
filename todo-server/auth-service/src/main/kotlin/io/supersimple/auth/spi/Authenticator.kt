package io.supersimple.auth.spi

import io.supersimple.auth.model.User

interface Authenticator {
    suspend fun verifyLogin(username: String, password: String): User
}
