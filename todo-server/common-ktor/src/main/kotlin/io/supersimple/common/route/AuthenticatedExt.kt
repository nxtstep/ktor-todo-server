package io.supersimple.common.route

import io.ktor.application.ApplicationCall
import io.ktor.auth.authenticate
import io.ktor.locations.post
import io.ktor.routing.Route
import io.ktor.util.pipeline.PipelineContext

public inline fun <reified T : Any> Route.authenticatedPost(
    configurations: String?,
    noinline body: suspend PipelineContext<Unit, ApplicationCall>.(T) -> Unit
): Route =
    authenticate(configurations) {
        post(body)
    }
