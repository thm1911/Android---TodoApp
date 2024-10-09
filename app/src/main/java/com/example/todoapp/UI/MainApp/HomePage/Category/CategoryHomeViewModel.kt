package com.example.todoapp.UI.MainApp.HomePage.Category

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.Database.Category.CategoryRepository
import com.example.todoapp.Model.CategoryAndTask
import com.example.todoapp.Utils.SharePref

class CategoryHomeViewModel(application: Application) : ViewModel() {
    private val categoryRepository: CategoryRepository
    val listCategory: LiveData<List<CategoryAndTask>>

    init {
        categoryRepository = CategoryRepository(application)
        val userId = SharePref.getUserIdFromPreferences(application)
        listCategory = categoryRepository.getAllCategoryAndTask(userId)
    }

    class CategoryHomeViewModelFactory(
        private val application: Application
    ) : ViewModelProvider
    .Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CategoryHomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CategoryHomeViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}