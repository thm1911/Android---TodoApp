package com.example.todoapp.UI.MainApp.HomePage.CreateTask

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todoapp.Database.Task.TaskRepository
import com.example.todoapp.Model.Task
import kotlinx.coroutines.launch

class BottomCreateTaskViewModel(context: Context): ViewModel() {
    private val taskRepository: TaskRepository

    init{
        taskRepository = TaskRepository(context)
    }

    fun addTask(task: Task) = viewModelScope.launch {
        taskRepository.insertTask(task)
    }

    class BottomCreateTaskViewModelFactory(private val context: Context):ViewModelProvider
    .Factory{
            override fun <T: ViewModel> create (modelClass: Class<T>): T{
                if(modelClass.isAssignableFrom(BottomCreateTaskViewModel::class.java)){
                    @Suppress("UNCHECKED_CAST")
                    return BottomCreateTaskViewModel(context) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
}