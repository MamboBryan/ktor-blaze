package com.example.data

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init() {
        Database.connect(hikari())

        transaction {
            SchemaUtils.create(Customers)
            SchemaUtils.create(Poems)
        }
    }

}

private fun hikari(): HikariDataSource {

    val config = HikariConfig().apply {

        driverClassName = System.getenv("JDBC_DRIVER")
        jdbcUrl = System.getenv("JDBC_DATABASE_URL")
        maximumPoolSize = 3
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"

    }

//    val user = System.getenv("DB_USER")
//    if (user != null) config.username = user
//
//    val password = System.getenv("DB_PASSWORD")
//    if (password != null) config.password = password

    config.validate()

    return HikariDataSource(config)

}