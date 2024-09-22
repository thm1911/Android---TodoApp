package com.example.todoapp.Database.Category

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.Model.Category

@Dao
interface CategoryDAO{
    @Query("SELECT * FROM Category WHERE userId = :userId")
    fun getAllCategory(userId: Long): LiveData<List<Category>>

    @Insert
    suspend fun insertCategory(category: Category)

    @Update
    suspend fun updateCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("SELECT * FROM Category WHERE id = :id")
    fun getCategoryById(id: Long): LiveData<Category>
}