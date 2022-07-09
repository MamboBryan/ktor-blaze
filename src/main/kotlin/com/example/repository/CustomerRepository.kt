package com.example.repository

import com.example.data.Customers
import com.example.models.Customer
import com.example.utils.query
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.InsertStatement

class CustomerRepository : Repository {

    override suspend fun create(firstName: String, lastName: String, email: String): Customer? {

        var statement: InsertStatement<Number>? = null

        query {
            statement = Customers.insert {
                it[Customers.firstName] = firstName
                it[Customers.lastName] = lastName
                it[Customers.email] = email
            }
        }

        return rowToCustomer(statement?.resultedValues?.first())

    }

    private fun rowToCustomer(row: ResultRow?): Customer? {
        if (row == null) return null

        return Customer(
            id = row[Customers.id],
            firstName = row[Customers.firstName],
            lastName = row[Customers.lastName],
            email = row[Customers.email],
        )

    }

    override suspend fun get(id: Int): Customer? {
        return query {
            Customers.select { Customers.id.eq(id) }.map { rowToCustomer(it) }.singleOrNull()
        }
    }

    override suspend fun getAll(): List<Customer?> {
        return query {
            Customers.selectAll().map { rowToCustomer(it) }
        }
    }


}