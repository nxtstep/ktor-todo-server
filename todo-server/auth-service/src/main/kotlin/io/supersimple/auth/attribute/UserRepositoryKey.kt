package io.supersimple.auth.attribute

import io.ktor.util.AttributeKey
import io.supersimple.auth.repository.UserRepository

val UserRepositoryKey = AttributeKey<UserRepository>("user-repository-key")
