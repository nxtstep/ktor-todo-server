package io.supersimple.common.serialization

import io.ktor.application.ApplicationCall
import io.ktor.request.receiveOrNull

/**
 * Catches all exceptions while parsing/receiving the desired model and return null in such case.
 */
suspend inline fun <reified T: Any> ApplicationCall.safeReceiveOrNull(): T? = try {
    receiveOrNull()
} catch (e: Exception) {
    null
}
