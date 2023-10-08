package com.polytechtest.di

import com.polytechtest.data.repositories.BookCategoryRepository
import com.polytechtest.data.repositories.BookCategoryRepositoryImpl
import com.polytechtest.data.repositories.BookListRepository
import com.polytechtest.data.repositories.BookListRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<BookListRepository> { BookListRepositoryImpl(get(), get(), get()) }
    single<BookCategoryRepository> { BookCategoryRepositoryImpl(get(), get(), get()) }
}