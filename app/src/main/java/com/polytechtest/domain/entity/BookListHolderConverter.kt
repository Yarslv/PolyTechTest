package com.polytechtest.domain.entity

import androidx.room.TypeConverter
import com.google.gson.Gson

class BookListHolderConverter {
    @TypeConverter
    fun fromBookListHolder(holder: BookListHolder): String {
        return Gson().toJson(holder)
    }

    @TypeConverter
    fun toBookListHolder(string: String): BookListHolder {
        return Gson().fromJson(string, BookListHolder::class.java)
    }
}