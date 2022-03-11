package io.supersimple.auth.dev

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.jwt.jwt
import io.supersimple.auth.attribute.AuthenticationKey
import io.supersimple.auth.attribute.UserRepositoryKey
import io.supersimple.auth.dev.feature.DevAuthenticator
import io.supersimple.auth.dev.repository.InMemoryUserRepository
import io.supersimple.auth.jwt.Config
import io.supersimple.auth.jwt.JWTTokenFactory
import io.supersimple.auth.model.User
import io.supersimple.auth.model.UserID
import io.supersimple.auth.route.AuthenticatorUserTokenGenerator
import io.supersimple.auth.route.userRouting
import java.util.UUID

fun Application.module() {
    val config = createJWTConfig()
    val userRepository = InMemoryUserRepository()
    val authenticator = DevAuthenticator(userRepository)
    attributes.put(UserRepositoryKey, userRepository)
    attributes.put(AuthenticationKey, "dev-jwt-auth")
    install(Authentication) {
        jwt(attributes[AuthenticationKey]) {
            with(config) {
                realm = myRealm
                verifier(
                    JWT
                        .require(jwtAlgorithm)
                        .withAudience(audience)
                        .withIssuer(issuer)
                        .build()
                )
            }
            validate { credential ->
                credential.subject?.let { userId ->
                    userRepository.getUser(UserID(UUID.fromString(userId)))
                }
            }
        }
    }
    val tokenGenerator = JWTTokenFactory(config)

    userRouting(object : AuthenticatorUserTokenGenerator {
        override suspend fun verifyLogin(username: String, password: String): User =
            authenticator.verifyLogin(username, password)

        override fun generateToken(user: User): String =
            tokenGenerator.generateToken(user)
    })
}

fun Application.createJWTConfig() = with(environment) {
    val secret = config.property("jwt.secret").getString()
    Config(
        issuer = config.property("jwt.issuer").getString(),
        audience = config.property("jwt.audience").getString(),
        myRealm = config.property("jwt.realm").getString(),
        jwtAlgorithm = Algorithm.HMAC512(secret)
    )
}
