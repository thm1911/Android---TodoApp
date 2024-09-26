package com.example.todoapp.UI.MainApp.HomePage.Category.ChooseCategory

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.Database.Category.CategoryRepository
import com.example.todoapp.Model.Category
import com.example.todoapp.Model.CategoryAndTask
import com.example.todoapp.UI.ShareViewModel

class ChooseCategoryViewModel(shareViewModel: ShareViewModel, context: Context) : ViewModel() {
    private val categoryRepository: CategoryRepository
    val listCategoryAndTask: LiveData<List<CategoryAndTask>>

    init {
        categoryRepository = CategoryRepository(context)
        val userId = shareViewModel.userId
        listCategoryAndTask = categoryRepository.getAllCategoryAndTask(userId)
    }

    class ChooseCategoryViewModelFactory(
        private val shareViewModel: ShareViewModel,
        private val context: Context
    ) : ViewModelProvider
    .Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ChooseCategoryViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ChooseCategoryViewModel(shareViewModel, context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}