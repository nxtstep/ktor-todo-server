package io.supersimple.auth.repository

import io.supersimple.auth.model.Password
import io.supersimple.auth.model.User
import io.supersimple.auth.model.UserID


interface UserRepository {
    suspend fun findUser(name: String): User?
    suspend fun getUser(userID: UserID): User?
    suspend fun getPassword(user: User): Password
    suspend fun add(user: User, password: Password): User
    suspend fun update(user: User)
}
