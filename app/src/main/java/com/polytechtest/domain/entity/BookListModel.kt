package com.polytechtest.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_lists_table")
data class BookListModel(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "encoded_name") val encoded_name: String,
    val list: BookListHolder,
){
    companion object {fun empty() = BookListModel(0, "", "", BookListHolder(emptyList()))}
}

