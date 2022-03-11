package io.supersimple.auth.route

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.locations.Location
import io.ktor.locations.post
import io.ktor.response.respond
import io.ktor.routing.routing
import io.supersimple.auth.spi.Authenticator
import io.supersimple.auth.spi.UserTokenFactory
import io.supersimple.common.api.error.MalformedRequestBodyException
import io.supersimple.common.serialization.safeReceiveOrNull

@Location("/login")
object LoginRequest

@kotlinx.serialization.Serializable
data class LoginCredentials(val username: String, val password: String)

interface AuthenticatorUserTokenGenerator : Authenticator, UserTokenFactory

fun Application.userRouting(authenticator: AuthenticatorUserTokenGenerator) {
    routing {
        post<LoginRequest> {
            val credentials: LoginCredentials = call.safeReceiveOrNull() ?: throw MalformedRequestBodyException()
            val user = authenticator.verifyLogin(credentials.username, credentials.password)
            val token = authenticator.generateToken(user)
            call.respond(token)
        }
    }
}
