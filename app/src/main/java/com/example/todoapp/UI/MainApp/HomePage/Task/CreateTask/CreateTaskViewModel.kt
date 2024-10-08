package com.example.todoapp.UI.MainApp.HomePage.Task.CreateTask

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todoapp.Database.Category.CategoryRepository
import com.example.todoapp.Database.Task.TaskRepository
import com.example.todoapp.Model.Task
import kotlinx.coroutines.launch

class CreateTaskViewModel(application: Application) : ViewModel() {
    private val taskRepository: TaskRepository
    private val categoryRepository: CategoryRepository

    init {
        taskRepository = TaskRepository(application)
        categoryRepository = CategoryRepository(application)
    }

    fun addTask(task: Task) = viewModelScope.launch {
        taskRepository.insertTask(task)
    }

    fun getCategoryById(category_id: Long) = categoryRepository.getCategoryById(category_id)

    class CreateTaskViewModelFactory(private val application: Application) : ViewModelProvider
    .Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CreateTaskViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CreateTaskViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}