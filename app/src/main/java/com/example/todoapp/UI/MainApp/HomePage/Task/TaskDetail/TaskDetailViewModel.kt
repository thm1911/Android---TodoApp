package com.example.todoapp.UI.MainApp.HomePage.Task.TaskDetail

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todoapp.Database.Category.CategoryRepository
import com.example.todoapp.Database.Task.TaskRepository
import com.example.todoapp.Model.Task
import kotlinx.coroutines.launch

class TaskDetailViewModel(application: Application) : ViewModel() {
    private val taskRepository: TaskRepository
    private val categoryRepository: CategoryRepository

    init {
        taskRepository = TaskRepository(application)
        categoryRepository = CategoryRepository(application)
    }

    fun getTaskById(id: Long) = taskRepository.getTaskById(id)

    fun updateTask(task: Task) = viewModelScope.launch {
        taskRepository.updateTask(task)
    }

    fun moveToTrash(id: Long) = viewModelScope.launch {
        taskRepository.moveToTrash(id)
    }

    fun doneTask(id: Long) = viewModelScope.launch {
        taskRepository.doneTask(id)
    }

    fun delete(id: Long) = viewModelScope.launch {
        taskRepository.deleteTask(id)
    }

    fun getCategoryById(categoryId: Long) = categoryRepository.getCategoryById(categoryId)

    class TaskDetailViewModelFactory(private val application: Application) : ViewModelProvider
    .Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TaskDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TaskDetailViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}