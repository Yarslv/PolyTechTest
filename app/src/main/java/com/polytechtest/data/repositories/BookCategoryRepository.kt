package com.polytechtest.data.repositories

import com.polytechtest.data.mapper.CategoriesMapper
import com.polytechtest.domain.entity.CategoryModel
import com.polytechtest.network.response.Either
import com.polytechtest.network.response.map
import com.polytechtest.network.service.NetworkService
import com.polytechtest.room.BookCategoriesDao
import com.polytechtest.util.CustomException

interface BookCategoryRepository {
    suspend fun getAllCategories(): Either<List<CategoryModel>>
}

class BookCategoryRepositoryImpl(
    private val networkService: NetworkService,
    private val bookCategoriesDao: BookCategoriesDao,
    private val categoriesMapper: CategoriesMapper,
) : BookCategoryRepository {
    override suspend fun getAllCategories(): Either<List<CategoryModel>> {
        val result = tryToLoadFromNetwork()
        return if (result.isSuccess) {
            saveToDataBase(result.success())
            result
        } else tryToLoadFromDataBase()
    }

    private suspend fun tryToLoadFromNetwork(): Either<List<CategoryModel>> {
        return try {
            networkService.getAllCategories().map { categoriesMapper.toDomain(it) }
        } catch (e: Throwable) {
            Either.failure(CustomException.NetworkException)
        }
    }

    private fun tryToLoadFromDataBase(): Either<List<CategoryModel>> {
        return try {
            Either.success(bookCategoriesDao.getAll())
        } catch (e: Throwable) {
            Either.failure(CustomException.RoomException)
        }
    }

    private fun saveToDataBase(list: List<CategoryModel>) {
        list.forEach {
            if (bookCategoriesDao.check(it)) bookCategoriesDao.update(it) else bookCategoriesDao.insertAll(
                it
            )
        }
    }
}