package com.example.todoapp.UI.MainApp.Calendar

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.Database.Category.CategoryRepository
import com.example.todoapp.Database.Task.TaskRepository
import com.example.todoapp.Model.Category
import com.example.todoapp.Model.Task
import com.example.todoapp.UI.ShareViewModel

class CalendarViewModel(shareViewModel: ShareViewModel, context: Context) : ViewModel() {
    private val taskRepository: TaskRepository
    private val categoryRepository: CategoryRepository
    val listTask: LiveData<List<Task>>
    val listCategory: LiveData<List<Category>>

    init {
        taskRepository = TaskRepository(context)
        categoryRepository = CategoryRepository(context)
        val userId = shareViewModel.userId
        listTask = taskRepository.getAllTask(userId)
        listCategory = categoryRepository.getAllCategory(userId)
    }

    class CalendarViewModelFactory(
        private val shareViewModel: ShareViewModel,
        private val context: Context
    ) : ViewModelProvider
    .Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CalendarViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CalendarViewModel(shareViewModel, context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}