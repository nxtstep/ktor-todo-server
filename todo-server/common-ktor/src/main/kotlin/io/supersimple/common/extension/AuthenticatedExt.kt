package io.supersimple.common.extension

import io.ktor.application.ApplicationCall
import io.ktor.auth.authenticate
import io.ktor.locations.post
import io.ktor.routing.Route
import io.ktor.util.pipeline.PipelineContext

public inline fun <reified T : Any> Route.authenticatedPost(
    configurations: String?,
    optional: Boolean = false,
    noinline body: suspend PipelineContext<Unit, ApplicationCall>.(T) -> Unit
): Route =
    authenticate(configurations, optional = optional) {
        post(body)
    }

// TODO add get/delete/put/patch
