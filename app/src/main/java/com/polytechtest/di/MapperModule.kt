package com.polytechtest.di

import com.polytechtest.data.mapper.BookListMapper
import com.polytechtest.data.mapper.CategoriesMapper
import org.koin.dsl.module

val mapperModule = module {
    single { CategoriesMapper() }
    single { BookListMapper() }
}