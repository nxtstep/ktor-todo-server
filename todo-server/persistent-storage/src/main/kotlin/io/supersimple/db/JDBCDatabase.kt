package io.supersimple.db

import io.supersimple.common.coroutines.CoroutineDispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.transaction

abstract class JDBCDatabase(
    private val dispatchers: CoroutineDispatchers = CoroutineDispatchers.default
) : SQLDatabase {
    abstract val db: Database

    override suspend fun <T> execTransaction(statement: Transaction.() -> T): T = withContext(dispatchers.io) {
        transaction(db, statement)
    }
}
