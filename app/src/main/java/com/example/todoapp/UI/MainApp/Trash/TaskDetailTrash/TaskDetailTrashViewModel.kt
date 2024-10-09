package com.example.todoapp.UI.MainApp.Trash.TaskDetailTrash

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.todoapp.Database.Category.CategoryRepository
import com.example.todoapp.Database.Task.TaskRepository
import com.example.todoapp.Model.Task
import kotlinx.coroutines.launch

class TaskDetailTrashViewModel(application: Application) : ViewModel() {
    private val taskRepository: TaskRepository
    private val categoryRepository: CategoryRepository

    init {
        taskRepository = TaskRepository(application)
        categoryRepository = CategoryRepository(application)
    }

    fun getTaskById(id: Long) = taskRepository.getTaskById(id)

    fun restoreTask(id: Long) = viewModelScope.launch {
        taskRepository.restoreTask(id)
    }

    fun delete(id: Long) = viewModelScope.launch {
        taskRepository.deleteTask(id)
    }

    fun getCategoryById(categoryId: Long) = categoryRepository.getCategoryById(categoryId)

    class TaskDetailTrashViewModelFactory(private val application: Application) : ViewModelProvider
    .Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TaskDetailTrashViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TaskDetailTrashViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}