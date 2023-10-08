package com.polytechtest.network.entity.catogiries

data class AnswerData(
    val copyright: String,
    val num_results: Int,
    val results: List<Result>,
    val status: String
)