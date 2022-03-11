package io.supersimple.common.api.meta

/**
 * Object versioning to indicate a particular revision of the object
 */
@JvmInline
@kotlinx.serialization.Serializable
value class ObjectVersion(val value: Int)
