package io.supersimple.db

import org.jetbrains.exposed.sql.Transaction

/**
 * Interface for an Exposed SQL Database
 */
interface SQLDatabase {
    suspend fun <T> execTransaction(statement: Transaction.() -> T): T
}
