package com.example.todoapp.UI.MainApp.HomePage.Category

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.Database.Category.CategoryRepository
import com.example.todoapp.Model.Category
import com.example.todoapp.UI.ShareViewModel

class CategoryViewModel(shareViewModel: ShareViewModel, context: Context): ViewModel() {
    private val categoryRepository: CategoryRepository
    val listCategory: LiveData<List<Category>>
    init {
        categoryRepository = CategoryRepository(context)
        val userId = shareViewModel.userId
        listCategory = categoryRepository.getAllCategory(userId)
    }

    class CategoryViewModelFactory(private val shareViewModel: ShareViewModel, private val context: Context): ViewModelProvider
    .Factory{
            override fun <T: ViewModel> create (modelClass: Class<T>): T{
                if(modelClass.isAssignableFrom(CategoryViewModel::class.java)){
                    @Suppress("UNCHECKED_CAST")
                    return CategoryViewModel(shareViewModel, context) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }

}