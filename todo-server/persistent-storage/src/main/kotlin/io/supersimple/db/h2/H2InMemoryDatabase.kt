package io.supersimple.db.h2

import io.supersimple.common.coroutines.CoroutineDispatchers
import io.supersimple.db.JDBCDatabase
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.transaction

class H2InMemoryDatabase(
    test: Boolean = false,
    dispatchers: CoroutineDispatchers = CoroutineDispatchers.default,
    initialSchemaCreation: (Transaction.() -> Unit)? = null,
) : JDBCDatabase(dispatchers = dispatchers) {
    override val db: Database by lazy {
        Database.connect(
            url = if (test) "jdbc:mem:test" else "jdbc:mem:regular",
            driver = "org.h2.Driver"
        ).also {
            transaction(it) {
                initialSchemaCreation?.invoke(this)
            }
        }
    }
}