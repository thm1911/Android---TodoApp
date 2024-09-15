package com.example.todoapp.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todoapp.Database.Task.TaskDAO
import com.example.todoapp.Database.User.UserDAO
import com.example.todoapp.Model.Task
import com.example.todoapp.Model.User

@Database(entities = [User::class, Task::class], version = 1)
@TypeConverters(Conveters::class)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun userDAO(): UserDAO
    abstract fun taskDAO(): TaskDAO

    companion object {
        @Volatile
        private var INSTANCE: TodoDatabase? = null
        fun getInstance(context: Context): TodoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java,
                    "todo_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}