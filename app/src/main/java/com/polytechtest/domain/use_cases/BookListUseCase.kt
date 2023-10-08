package com.polytechtest.domain.use_cases

import com.polytechtest.data.repositories.BookListRepository
import com.polytechtest.domain.entity.BookListModel
import com.polytechtest.network.response.Either
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BookListUseCase(private val repository: BookListRepository) {

    suspend fun getBookList(encodedName: String): Either<BookListModel> {
        return try {
            withContext(Dispatchers.IO) { repository.getBookList(encodedName) }
        } catch (e: Throwable) {
            Either.failure(e)
        }
    }
}