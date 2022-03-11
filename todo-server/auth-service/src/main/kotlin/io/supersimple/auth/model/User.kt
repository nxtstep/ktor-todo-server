package io.supersimple.auth.model

import io.ktor.auth.Principal
import io.supersimple.common.security.Hashing
import io.supersimple.common.serialization.UUIDSerializer
import java.time.LocalDate
import java.util.UUID

@JvmInline
@kotlinx.serialization.Serializable
value class UserID(
    @kotlinx.serialization.Serializable(with = UUIDSerializer::class)
    val value: UUID)

data class User(
    val id: UserID = UserID(UUID.randomUUID()),
    val name: String,
    val created: LocalDate = LocalDate.now(),
    val lastLogin: LocalDate? = null,
): Principal

data class Password(val userId: UserID, val salt: String, val hash: String)

fun Password.validate(password: String): Boolean =
    (Hashing.SHA256.hash(salt + password) == hash)
