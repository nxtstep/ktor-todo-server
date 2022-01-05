package io.supersimple.todo.controllers

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.Application
import io.ktor.auth.jwt.JWTAuthenticationProvider
import io.ktor.auth.jwt.JWTCredential
import io.supersimple.todo.api.error.LoginException
import io.supersimple.todo.data.model.User
import io.supersimple.todo.data.model.UserID
import io.supersimple.todo.data.repositories.users.UserRepository
import io.supersimple.todo.data.model.validate
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import java.time.LocalDate
import java.util.Date
import java.util.UUID

interface JWTTokenFactory {
    fun generateToken(user: User): String
}

fun Application.AuthenticationController(): AuthenticationController = with(environment) {
    val secret = config.property("jwt.secret").getString()
    val issuer = config.property("jwt.issuer").getString()
    val audience = config.property("jwt.audience").getString()
    val myRealm = config.property("jwt.realm").getString()

    val userRepository: UserRepository by closestDI().instance()
    return AuthenticationController(
        userRepository = userRepository,
        jwtAlgorithm = Algorithm.HMAC512(secret),
        issuer = issuer,
        audience = audience,
        myRealm = myRealm,
    )
}

class AuthenticationController(
    private val userRepository: UserRepository,
    private val jwtAlgorithm: Algorithm,
    private val issuer: String,
    private val audience: String,
    private val myRealm: String,
    private val expirationInterval: Int = 3_600_000 * 24, // 24 hours
    private val tokenFactory: JWTTokenFactory = object : JWTTokenFactory {
        override fun generateToken(user: User): String = JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withSubject(user.id.value.toString())
            .withClaim(CLAIM_USERNAME, user.name)
            .withExpiresAt(expiresAt())
            .sign(jwtAlgorithm)

        private fun expiresAt() = Date(System.currentTimeMillis() + expirationInterval)
    }
) {

    companion object {
        const val CLAIM_USERNAME = "username"
        const val authKey = "jwt-todo"
    }

    private val jwtVerifier: JWTVerifier = JWT
        .require(jwtAlgorithm)
        .withIssuer(issuer)
        .build()

    suspend fun handleLogin(username: String, password: String): String =
        userRepository.findUser(username)?.takeIf { user ->
            userRepository.getPassword(user).validate(password)
        }?.let { user ->
            userRepository.update(user.copy(lastLogin = LocalDate.now()))
            tokenFactory.generateToken(user)
        } ?: throw LoginException()

    private suspend fun validateCredential(credential: JWTCredential): User? =
        credential.subject?.let { userId ->
            userRepository.getUser(UserID(UUID.fromString(userId)))
        }

    fun configure(config: JWTAuthenticationProvider.Configuration) = with(config) {
        verifier(jwtVerifier)
        realm = myRealm
        validate {
            validateCredential(it)
        }
    }
}
