package com.polytechtest.data.repositories

import com.polytechtest.data.mapper.BookListMapper
import com.polytechtest.domain.entity.BookListModel
import com.polytechtest.network.response.Either
import com.polytechtest.network.response.map
import com.polytechtest.network.service.NetworkService
import com.polytechtest.room.BookListDao
import com.polytechtest.util.CustomException


interface BookListRepository {
    suspend fun getBookList(encodedName: String): Either<BookListModel>
}

class BookListRepositoryImpl(
    private val networkService: NetworkService,
    private val bookListDao: BookListDao,
    private val bookListMapper: BookListMapper,
) : BookListRepository {

    override suspend fun getBookList(encodedName: String): Either<BookListModel> {
        val result = tryToLoadFromNetwork(encodedName)

        return if (result.isSuccess) {
            saveToDataBase(result.success())
            result
        } else tryToLoadFromDataBase(encodedName)
    }

    private suspend fun tryToLoadFromNetwork(encodedName: String): Either<BookListModel> {
        return try {
            networkService.getBookListByName(encodedName).map { bookListMapper.toDomain(it) }
        } catch (e: Throwable) {
            Either.failure(CustomException.NetworkException)
        }
    }

    private fun tryToLoadFromDataBase(encodedName: String): Either<BookListModel> {
        return try {
            Either.success(bookListDao.get(encodedName = encodedName))
        } catch (e: Throwable) {
            Either.failure(CustomException.RoomException)
        }
    }

    private fun saveToDataBase(bookList: BookListModel) {
        if (bookListDao.check(bookList)) bookListDao.update(bookList) else bookListDao.insertAll(
            bookList
        )
    }
}