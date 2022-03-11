package io.supersimple.auth.spi

import io.supersimple.auth.model.User

interface UserTokenFactory {
    fun generateToken(user: User): String
}
