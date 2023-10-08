package com.polytechtest.network.entity.catogiries.book_list

data class BookListAnswer(
    val copyright: String,
    val last_modified: String,
    val num_results: Int,
    val results: Results,
    val status: String
)