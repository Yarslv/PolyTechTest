package com.polytechtest.di

import com.polytechtest.domain.use_cases.BookListUseCase
import com.polytechtest.domain.use_cases.CategoriesUseCase
import org.koin.dsl.module

val providerModule = module {
    single { CategoriesUseCase(get()) }
    single { BookListUseCase(get()) }
}