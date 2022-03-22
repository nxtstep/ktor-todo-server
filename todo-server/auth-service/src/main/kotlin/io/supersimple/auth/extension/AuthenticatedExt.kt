package io.supersimple.auth.extension

import io.ktor.application.ApplicationCall
import io.ktor.auth.*
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.routing.*
import io.ktor.util.pipeline.PipelineContext
import io.supersimple.auth.attribute.AuthenticationKey
import io.supersimple.auth.model.User

public inline fun <reified T : Any> Route.authenticatedPost(
    configurations: String? = application.attributes[AuthenticationKey],
    noinline body: suspend PipelineContext<Unit, ApplicationCall>.(T, User) -> Unit
): Route =
    authenticate(configurations) {
        post<T> { call ->
            context.principal<User>()?.let { user ->
                body(call, user)
            }
        }
    }

public inline fun <reified T : Any> Route.authenticatedGet(
    configurations: String? = application.attributes[AuthenticationKey],
    noinline body: suspend PipelineContext<Unit, ApplicationCall>.(T, User) -> Unit
): Route =
    authenticate(configurations, optional = false) {
        get<T> { call ->
            context.principal<User>()?.let { user ->
                body(call, user)
            }
        }
    }

public inline fun <reified T : Any> Route.optionalAuthenticatedGet(
    configurations: String? = application.attributes[AuthenticationKey],
    noinline body: suspend PipelineContext<Unit, ApplicationCall>.(T, User?) -> Unit
): Route =
    authenticate(configurations, optional = true) {
        get<T> { body ->
            body(body, context.principal())
        }
    }

// TODO add delete/put/patch
