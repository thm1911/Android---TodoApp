package com.example.todoapp.UI.MainApp.HomePage.Category.CreateCategory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.todoapp.Database.Category.CategoryRepository
import com.example.todoapp.Model.Category
import kotlinx.coroutines.launch

class BottomCreateCategoryViewModel(context: Context) : ViewModel() {
    private val categoryRepository: CategoryRepository

    init {
        categoryRepository = CategoryRepository(context)
    }

    fun insertCategory(category: Category) = viewModelScope.launch {
        categoryRepository.insertCategory(category)
    }

    fun isNameExist(name: String, userId: Long, CallBack: (Boolean) -> Unit) =
        viewModelScope.launch {
            val check = categoryRepository.isNameExists(name, userId)
            CallBack(check)
        }

    fun getCategoryById(id: Long) = categoryRepository.getCategoryById(id)

    fun update(category: Category) = viewModelScope.launch {
        categoryRepository.updateCategory(category)
    }

    class BottomCreateCategoryViewModelFactory(private val context: Context) : ViewModelProvider
    .Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BottomCreateCategoryViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return BottomCreateCategoryViewModel(context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}