package com.example.todoapp.UI.MainApp.HomePage.Category.ChooseCategory

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.Database.Category.CategoryRepository
import com.example.todoapp.Model.Category
import com.example.todoapp.Model.CategoryAndTask
import com.example.todoapp.Utils.SharePref

class ChooseCategoryViewModel(application: Application) : ViewModel() {
    private val categoryRepository: CategoryRepository
    val listCategoryAndTask: LiveData<List<CategoryAndTask>>

    init {
        categoryRepository = CategoryRepository(application)
        val userId = SharePref.getUserIdFromPreferences(application)
        listCategoryAndTask = categoryRepository.getAllCategoryAndTask(userId)
    }

    class ChooseCategoryViewModelFactory(
        private val application: Application
    ) : ViewModelProvider
    .Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ChooseCategoryViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ChooseCategoryViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}