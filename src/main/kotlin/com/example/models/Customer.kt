package com.example.models

import com.google.gson.annotations.SerializedName

data class Customer(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String
)

val customerStorage = mutableListOf<Customer>()