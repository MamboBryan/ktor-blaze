package com.example.repository

import com.example.data.Customers
import com.example.data.Poems
import com.example.models.Poem
import com.example.utils.query
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.InsertStatement

class PoemRepository {

    private fun mapToPoem(row: ResultRow?): Poem? {
        if (row == null) return null

        return Poem(
            id = row[Poems.id], userId = row[Poems.userId], content = row[Poems.content]
        )

    }

    suspend fun create(userId: Int, content: String): Poem? {
        var statement: InsertStatement<Number>? = null

        query {

            statement = Poems.insert {
                it[Poems.content] = content
                it[Poems.userId] = userId
            }

        }

        return mapToPoem(statement?.resultedValues?.first())

    }

    suspend fun getAll(): List<Poem?> {

        return query {
            Poems.selectAll().map { mapToPoem(it) }
        }

    }

    suspend fun getAllCustomerPoems(id: Int): List<Poem?> {
        return query {
            Poems.select(where = Poems.userId.eq(id)).map { mapToPoem(it) }
        }
    }

}