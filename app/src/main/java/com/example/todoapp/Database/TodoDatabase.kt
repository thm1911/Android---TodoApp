package com.example.todoapp.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todoapp.Database.User.UserDAO
import com.example.todoapp.Model.User

@Database(entities = [User::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun userDAO(): UserDAO

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