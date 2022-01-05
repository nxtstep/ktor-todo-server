package io.supersimple.todo.routes.user

import io.supersimple.todo.data.repositories.users.InMemoryUserRepository
import io.supersimple.todo.data.repositories.users.UserRepository
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

fun createUserModule(userRepo: () -> UserRepository = { InMemoryUserRepository() }) = DI.Module("user-module") {
    bind<UserRepository>() with singleton { userRepo() }
}
