package com.example.todoapp.Database.Task

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.Model.Task

@Dao
interface TaskDAO {
    @Query("SELECT * FROM Task WHERE isDelete = 0 AND userId = :userId")
    fun getAllTask(userId: Long): LiveData<List<Task>>

    @Query("SELECT * FROM Task WHERE isDelete = 1 AND userId = :userId")
    fun getAllDeleteTask(userId: Long): LiveData<List<Task>>

    @Query("UPDATE Task SET isDelete = 1 WHERE id = :id")
    suspend fun moveToTrash(id: Long)

    @Query("UPDATE Task SET isDelete = 0 AND userId = :userId")
    suspend fun getAllRestore(userId: Long)

    @Query("UPDATE Task SET isDone = 1 WHERE id = :id")
    suspend fun doneTask(id: Long)

    @Query("UPDATE TASK SET isDelete = 0 WHERE id = :id")
    suspend fun restoreTask(id: Long)

    @Insert
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Query("DELETE FROM Task WHERE id = :id")
    suspend fun deleteTask(id: Long)

    @Query("SELECT * FROM Task WHERE id = :id")
    fun getTaskById(id: Long): LiveData<Task>

    @Query("SELECT * FROM Task WHERE categoryId = :categoryId AND userId = :userId")
    fun getAllTaskByCategory(categoryId: Long, userId: Long): LiveData<List<Task>>

}