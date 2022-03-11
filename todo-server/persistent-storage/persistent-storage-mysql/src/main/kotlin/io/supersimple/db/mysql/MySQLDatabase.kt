package io.supersimple.db.mysql

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.supersimple.common.coroutines.CoroutineDispatchers
import io.supersimple.db.JDBCDatabase
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.transaction

class MySQLDatabase(
    host: String,
    port: Int = 3306,
    database: String,
    user: String = "",
    passwd: String = "",
    maxPoolSize: Int = 10,
    dispatchers: CoroutineDispatchers = CoroutineDispatchers.default,
    initialSchemaCreation: (Transaction.() -> Unit)? = null,
) : JDBCDatabase(dispatchers = dispatchers) {
    override val db: Database by lazy {
        val url = "jdbc:mysql://$host:$port/$database"
        val driver = "com.mysql.cj.jdbc.Driver"
        val config = HikariConfig().apply {
            jdbcUrl = url
            driverClassName = driver
            username = user
            password = passwd
            maximumPoolSize = maxPoolSize
        }
        val dataSource = HikariDataSource(config)
        Database.connect(dataSource).also {
            transaction(it) {
                initialSchemaCreation?.invoke(this)
            }
        }
    }
}
