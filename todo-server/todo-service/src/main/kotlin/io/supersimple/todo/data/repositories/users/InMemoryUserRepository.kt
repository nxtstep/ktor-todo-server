package io.supersimple.todo.data.repositories.users

import io.supersimple.common.security.Hashing
import io.supersimple.todo.data.model.Password
import io.supersimple.todo.data.model.User
import io.supersimple.todo.data.model.UserID
import java.util.UUID

class InMemoryUserRepository : UserRepository {
    private val users = mutableMapOf<String, Pair<User, Password>>().apply {
        val salt = "pannekoek"
        val userID = UserID(UUID.fromString("7ec25a1c-5856-4459-9316-8980507bee81"))
        put(
            "pete", Pair(
                User(userID, "pete"),
                Password(
                    userID, salt, Hashing.SHA256.hash("${salt}secret")
                )
            )
        )
    }

    override suspend fun findUser(name: String): User? =
        users[name]?.first

    override suspend fun getUser(userID: UserID): User? =
        users.values.firstOrNull { it.first.id == userID }?.first

    override suspend fun getPassword(user: User): Password =
        users[user.name]!!.second

    override suspend fun add(user: User, password: Password): User {
        users[user.name] = Pair(user, password)
        return user
    }

    override suspend fun update(user: User) {
        users[user.name] = Pair(user, getPassword(user))
    }
}
