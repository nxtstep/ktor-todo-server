package io.supersimple.todo.data.repositories.users

import io.supersimple.todo.data.model.Password
import io.supersimple.todo.data.model.User
import io.supersimple.todo.data.model.UserID

interface UserRepository {
    suspend fun findUser(name: String): User?
    suspend fun getUser(userID: UserID): User?
    suspend fun getPassword(user: User): Password
    suspend fun add(user: User, password: Password): User
    suspend fun update(user: User)
}
