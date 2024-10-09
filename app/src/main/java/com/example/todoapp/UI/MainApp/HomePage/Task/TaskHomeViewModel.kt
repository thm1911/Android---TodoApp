package com.example.todoapp.UI.MainApp.HomePage.Task

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todoapp.Database.Category.CategoryRepository
import com.example.todoapp.Database.Task.TaskRepository
import com.example.todoapp.Database.User.UserRepository
import com.example.todoapp.Model.Category
import com.example.todoapp.Model.Task
import com.example.todoapp.Utils.SharePref
import kotlinx.coroutines.launch

class TaskHomeViewModel(application: Application) : ViewModel() {
    private val taskRepository: TaskRepository
    private val userRepository: UserRepository
    private val categoryRepository: CategoryRepository
    var listTask: LiveData<List<Task>>

    init {
        taskRepository = TaskRepository(application)
        userRepository = UserRepository(application)
        categoryRepository = CategoryRepository(application)
        val userId = SharePref.getUserIdFromPreferences(application)
        listTask = taskRepository.getAllTask(userId)
    }

    fun getTheme(id: Long, callBack: (Int) -> Unit) = viewModelScope.launch {
        val theme = userRepository.getTheme(id)
        callBack(theme)
    }

    fun delete(category: Category) = viewModelScope.launch {
        categoryRepository.deleteCategory(category)
    }

    fun getTaskByCategory(categoryId: Long, userId: Long) =
        taskRepository.getTaskByCategory(categoryId, userId)

    fun getCategoryById(id: Long) = categoryRepository.getCategoryById(id)
    class TaskHomeViewModelFactory(
        private val application: Application
    ) : ViewModelProvider
    .Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TaskHomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TaskHomeViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}