package com.example.data

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object Poems : Table() {

    val id: Column<Int> = integer("id").autoIncrement()
    override val primaryKey: PrimaryKey = PrimaryKey(id)

    val content = varchar("content", length = 128)
    val userId = integer("userId").references(ref = Customers.id, onDelete = ReferenceOption.CASCADE)
//    val user : Column<String> = reference()

}