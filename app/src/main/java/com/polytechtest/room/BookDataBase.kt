package com.polytechtest.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.polytechtest.domain.entity.BookListModel
import com.polytechtest.domain.entity.BookListHolderConverter
import com.polytechtest.domain.entity.CategoryModel

@Database(entities = [BookListModel::class, CategoryModel::class], version = 2)
@TypeConverters(BookListHolderConverter::class)
abstract class BookDataBase : RoomDatabase() {
    abstract fun bookListDao(): BookListDao
    abstract fun categoriesDao(): BookCategoriesDao
}

