package com.example.todoapp.UI.MainApp.Calendar

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.Database.Category.CategoryRepository
import com.example.todoapp.Database.Task.TaskRepository
import com.example.todoapp.Model.Category
import com.example.todoapp.Model.Task
import com.example.todoapp.Utils.SharePref

class CalendarViewModel(application: Application) : ViewModel() {
    private val taskRepository: TaskRepository
    private val categoryRepository: CategoryRepository
    val listTask: LiveData<List<Task>>
    val listCategory: LiveData<List<Category>>

    init {
        taskRepository = TaskRepository(application)
        categoryRepository = CategoryRepository(application)
        val userId = SharePref.getUserIdFromPreferences(application)
        listTask = taskRepository.getAllTaskCalendar(userId)
        listCategory = categoryRepository.getAllCategory(userId)
    }

    class CalendarViewModelFactory(
        private val application: Application
    ) : ViewModelProvider
    .Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CalendarViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CalendarViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}