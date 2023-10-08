package com.polytechtest.domain.use_cases

import com.polytechtest.data.repositories.BookCategoryRepository
import com.polytechtest.domain.entity.CategoryModel
import com.polytechtest.network.response.Either
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoriesUseCase(private val repository: BookCategoryRepository) {

    suspend fun getCategories(): Either<List<CategoryModel>> {
        return try {
            withContext(Dispatchers.IO) { repository.getAllCategories() }
        } catch (e: Throwable) {
            Either.failure(e)
        }
    }
}