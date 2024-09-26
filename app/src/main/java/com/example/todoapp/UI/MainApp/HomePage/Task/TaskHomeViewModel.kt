package com.example.todoapp.UI.MainApp.HomePage.Task

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
import com.example.todoapp.UI.ShareViewModel
import kotlinx.coroutines.launch

class TaskHomeViewModel(shareViewModel: ShareViewModel, context: Context) : ViewModel() {
    private val taskRepository: TaskRepository
    private val userRepository: UserRepository
    private val categoryRepository: CategoryRepository
    var listTask: LiveData<List<Task>>

    init {
        taskRepository = TaskRepository(context)
        userRepository = UserRepository(context)
        categoryRepository = CategoryRepository(context)
        val userId = shareViewModel.userId
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
        private val shareViewModel: ShareViewModel,
        private val context: Context
    ) : ViewModelProvider
    .Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TaskHomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TaskHomeViewModel(shareViewModel, context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}