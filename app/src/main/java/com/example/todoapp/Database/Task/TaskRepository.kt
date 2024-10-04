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

    fun getAllTask(userId: Long): LiveData<List<Task>> = taskDAO.getAllTask(userId)
    fun getAllTaskCalendar(userId: Long): LiveData<List<Task>> = taskDAO.getAllTaskCalendar(userId)
    fun getAllDeleteTask(userId: Long): LiveData<List<Task>> = taskDAO.getAllDeleteTask(userId)
    suspend fun getAllRestore(userId: Long) = taskDAO.getAllRestore(userId)
    suspend fun doneTask(id: Long) = taskDAO.doneTask(id)
    suspend fun restoreTask(id: Long) = taskDAO.restoreTask(id)
    suspend fun moveToTrash(taskId: Long) = taskDAO.moveToTrash(taskId)
    suspend fun insertTask(task: Task) = taskDAO.insertTask(task)
    suspend fun updateTask(task: Task) = taskDAO.updateTask(task)
    suspend fun deleteTask(id: Long) = taskDAO.deleteTask(id)
    fun getTaskById(id: Long): LiveData<Task> = taskDAO.getTaskById(id)
    fun getTaskByCategory(categoryId: Long, userId: Long): LiveData<List<Task>> =
        taskDAO.getAllTaskByCategory(categoryId, userId)
}