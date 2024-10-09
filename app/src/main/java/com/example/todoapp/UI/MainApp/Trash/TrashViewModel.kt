package com.example.todoapp.UI.MainApp.Trash

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todoapp.Database.Task.TaskRepository
import com.example.todoapp.Database.User.UserRepository
import com.example.todoapp.Model.Task
import com.example.todoapp.Utils.SharePref
import kotlinx.coroutines.launch

class TrashViewModel(application: Application) : ViewModel() {
    private val taskRepository: TaskRepository
    private val userRepository: UserRepository
    val listDeleteTask: LiveData<List<Task>>
    val userId = SharePref.getUserIdFromPreferences(application)

    init {
        taskRepository = TaskRepository(application)
        userRepository = UserRepository(application)
        listDeleteTask = taskRepository.getAllDeleteTask(userId)
    }

    fun getTheme(id: Long, callBack: (Int) -> Unit) = viewModelScope.launch {
        val theme = userRepository.getTheme(id)
        callBack(theme)
    }

    fun getAllRestore() = viewModelScope.launch {
        taskRepository.getAllRestore(userId)
    }

    class TrashViewModelFactory(
        private val application: Application
    ) : ViewModelProvider
    .Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TrashViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TrashViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}