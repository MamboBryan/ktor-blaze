package com.example.repository

import com.example.models.Customer

interface Repository {

    suspend fun create(firstName: String, lastName: String, email: String): Customer?

    suspend fun get(id: Int): Customer?

    suspend fun getAll(): List<Customer?>

}