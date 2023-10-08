package com.polytechtest.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.polytechtest.domain.entity.CategoryModel

@Dao
interface BookCategoriesDao {
    @Query("SELECT * FROM categories_table")
    fun getAll(): List<CategoryModel>

    fun check(model: CategoryModel): Boolean {
        return checkIfExist(model.encodedName) != 0
    }

    @Query("SELECT * FROM categories_table WHERE encoded_name = :name")
    fun checkIfExist(name: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: CategoryModel)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(vararg users: CategoryModel)
}