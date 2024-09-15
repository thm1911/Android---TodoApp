package com.example.todoapp.Database.Task

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.todoapp.Database.TodoDatabase
import com.example.todoapp.Model.Task

class TaskRepository(context: Context) {
    private val taskDAO: TaskDAO
    init {
        taskDAO = TodoDatabase.getInstance(context).taskDAO()
    }

    fun getAllTask(): LiveData<List<Task>> = taskDAO.getAllTask()
    suspend fun insertTask(task: Task) = taskDAO.insertTask(task)
    suspend fun updateTask(task: Task) = taskDAO.updateTask(task)
    suspend fun deleteTask(task: Task) = taskDAO.deleteTask(task)
}