package com.polytechtest.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.polytechtest.domain.entity.BookListModel

@Dao
interface BookListDao {

    @Query("SELECT * FROM book_lists_table WHERE encoded_name = :encodedName")
    fun get(encodedName: String): BookListModel

    fun check(model: BookListModel): Boolean {
        return checkIfExist(model.title) != 0
    }

    @Query("SELECT * FROM book_lists_table WHERE encoded_name = :encodedName")
    fun checkIfExist(encodedName: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: BookListModel)


    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(vararg users: BookListModel)
}