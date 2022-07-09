package com.example.data

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Customers : Table() {

    val id: Column<Int> = integer("id").autoIncrement()
    val firstName = varchar("first_name", 128)
    val lastName = varchar("last_name", 128)
    val email = varchar("email", 128).uniqueIndex()

    override val primaryKey: PrimaryKey = PrimaryKey(id)

}