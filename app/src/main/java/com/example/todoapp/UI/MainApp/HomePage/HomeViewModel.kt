package com.example.todoapp.UI.MainApp.HomePage

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todoapp.Database.Category.CategoryRepository
import com.example.todoapp.Database.Task.TaskRepository
import com.example.todoapp.Database.User.UserRepository
import com.example.todoapp.Model.CategoryAndTask
import com.example.todoapp.Model.Task
import com.example.todoapp.Model.User
import com.example.todoapp.Utils.SharePref
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : ViewModel() {
    private val taskRepository: TaskRepository
    private val categoryRepository: CategoryRepository
    private val userRepository: UserRepository
    val listCategoryAndTask: LiveData<List<CategoryAndTask>>
    val listTask: LiveData<List<Task>>
    val listUser: LiveData<User>

    init {
        taskRepository = TaskRepository(application)
        categoryRepository = CategoryRepository(application)
        userRepository = UserRepository(application)
        val userId = SharePref.getUserIdFromPreferences(application)
        listCategoryAndTask = categoryRepository.getAllCategoryAndTask(userId)
        listTask = taskRepository.getAllTask(userId)
        listUser = userRepository.getUserById(userId)
    }

    fun getTheme(id: Long, callBack: (Int) -> Unit) = viewModelScope.launch {
        val theme = userRepository.getTheme(id)
        callBack(theme)
    }

    class HomeViewModelFactory(
        private val application: Application
    ) : ViewModelProvider
    .Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}