package com.polytechtest.domain.entity

data class BookModel(
    val name: String,
    val description: String,
    val author: String,
    val publisher: String,
    val imageLink: String,
    val rank: String,
    val buyLink: String,
)