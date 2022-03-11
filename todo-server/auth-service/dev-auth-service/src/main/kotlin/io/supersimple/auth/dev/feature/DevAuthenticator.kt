package io.supersimple.auth.dev.feature

import io.supersimple.auth.api.error.LoginException
import io.supersimple.auth.model.User
import io.supersimple.auth.model.validate
import io.supersimple.auth.repository.UserRepository
import io.supersimple.auth.spi.Authenticator
import java.time.LocalDate

typealias LocalDateProvider = () -> LocalDate

class DevAuthenticator(
    private val userRepository: UserRepository,
    private val currentDateProvider: LocalDateProvider = LocalDate::now,
) : Authenticator {

    override suspend fun verifyLogin(username: String, password: String): User =
        userRepository.findUser(username)?.takeIf { user ->
            userRepository.getPassword(user).validate(password)
        }?.also { user ->
            userRepository.update(user.copy(lastLogin = currentDateProvider()))
        } ?: throw LoginException()
}
