package com.example.todoapp.Database.Category

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.RoomDatabase
import com.example.todoapp.Database.TodoDatabase
import com.example.todoapp.Model.Category

class CategoryRepository(context: Context){
    private val categoryDAO: CategoryDAO
    init {
        categoryDAO = TodoDatabase.getInstance(context).categoryDAO()
    }

    fun getAllCategory(userId: Long): LiveData<List<Category>> = categoryDAO.getAllCategory(userId)
    suspend fun insertCategory(category: Category) = categoryDAO.insertCategory(category)
    suspend fun updateCategory(category: Category) = categoryDAO.updateCategory(category)
    fun getCategoryById(id: Long): LiveData<Category> = categoryDAO.getCategoryById(id)
}