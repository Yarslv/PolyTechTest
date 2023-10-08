package com.polytechtest.network.service

import com.polytechtest.network.entity.catogiries.book_list.BookListAnswer
import com.polytechtest.network.entity.catogiries.CategoriesAnswer
import com.polytechtest.network.response.Either

class NetworkService(private val service: Api, private val apiAnswer: ApiAnswerService) {

    suspend fun getAllCategories(): Either<CategoriesAnswer> {
        return try {
            val response = service.getCategories()
            return apiAnswer.extractAnswer(response)
        } catch (e: Throwable) {
            Either.failure(e)
        }
    }

    suspend fun getBookListByName(encodedName: String): Either<BookListAnswer> {
        return try {
            val response = service.getBookListByName(encodedName)
            return apiAnswer.extractAnswer(response)
        } catch (e: Throwable) {
            Either.failure(e)
        }
    }
}