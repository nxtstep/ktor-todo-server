package io.supersimple.todo.routes.user

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.locations.Location
import io.ktor.locations.post
import io.ktor.response.respond
import io.ktor.routing.routing
import io.supersimple.common.serialization.safeReceiveOrNull
import io.supersimple.todo.api.error.MalformedRequestBodyException
import io.supersimple.todo.controllers.AuthenticationController

@Location("/login")
object LoginRequest

@kotlinx.serialization.Serializable
data class LoginCredentials(val username: String, val password: String)

fun Application.userRouting() {
//    subDI(closestDI()) {
//        import(userModule)
//    }
    val controller = AuthenticationController()
    routing {
        post<LoginRequest> {
            val credentials: LoginCredentials = call.safeReceiveOrNull() ?: throw MalformedRequestBodyException()
            val token = controller.handleLogin(credentials.username, credentials.password)

            call.respond(token)
        }
    }
}
