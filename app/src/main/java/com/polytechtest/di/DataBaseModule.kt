package com.polytechtest.di

import androidx.room.Room
import com.polytechtest.room.BookDataBase
import com.polytechtest.util.Constants
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataBaseModule = module {
    single(named("dataBase")) {
        Room.databaseBuilder(get(), BookDataBase::class.java, Constants.DATABASE_NAME)
//            .fallbackToDestructiveMigration()
            .build()
    }

    single { (get(named("dataBase")) as BookDataBase).bookListDao() }
    single { (get(named("dataBase")) as BookDataBase).categoriesDao() }
}