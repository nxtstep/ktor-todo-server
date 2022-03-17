package io.supersimple.auth.extension

import io.ktor.application.ApplicationCall
import io.ktor.auth.*
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.routing.*
import io.ktor.util.pipeline.PipelineContext
import io.supersimple.auth.api.error.AuthenticationException
import io.supersimple.auth.attribute.AuthenticationKey
import io.supersimple.auth.model.User

public inline fun <reified T : Any> Route.authenticatedPost(
    configurations: String? = application.attributes[AuthenticationKey],
    noinline body: suspend PipelineContext<Unit, ApplicationCall>.(T) -> Unit
): Route =
    authenticate(configurations) {
        post(body)
    }

public inline fun <reified T : Any> Route.authenticatedGet(
    configurations: String? = application.attributes[AuthenticationKey],
    optional: Boolean = false,
    noinline body: suspend PipelineContext<Unit, ApplicationCall>.(T) -> Unit
): Route =
    authenticate(configurations, optional = optional) {
        get(body)
    }

// TODO add delete/put/patch

public val PipelineContext<Unit, ApplicationCall>.authenticatedUser: User? get() = (this.context.principal() as? User)
