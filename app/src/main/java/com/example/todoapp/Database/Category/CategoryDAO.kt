package com.example.todoapp.Database.Category

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.Model.Category
import com.example.todoapp.Model.CategoryAndTask
import com.example.todoapp.Model.Task

@Dao
interface CategoryDAO {
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

    @Query("SELECT EXISTS(SELECT 1 FROM Category WHERE name = :name AND userId = :userId)")
    suspend fun isNameExists(name: String, userId: Long): Boolean

    @Query("SELECT Category.id as id, Category.name as nameCategory, Category.color as color, Count(Task.id) as totalTask FROM Category LEFT JOIN Task ON Category.id = Task.categoryId AND Task.isDelete = 0 WHERE Category.userId = :userId GROUP BY Category.id")
    fun getAllCategoryAndTask(userId: Long): LiveData<List<CategoryAndTask>>

}