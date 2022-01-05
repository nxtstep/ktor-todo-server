package io.supersimple.todo.data.model.meta

import kotlinx.serialization.Serializable

/**
 * Object versioning to indicate a particular revision of the object
 */
@JvmInline
@Serializable
value class ObjectVersion(val value: Int)
