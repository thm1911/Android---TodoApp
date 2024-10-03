package com.example.todoapp.Database.User

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.Model.User

@Dao
interface UserDAO {
    @Insert
    suspend fun insertUser(user: User): Long

    @Query("UPDATE User SET password = :newPassword WHERE id = :id")
    suspend fun updatePassword(id: Long, newPassword: String)

    @Query("SELECT EXISTS(SELECT 1 FROM User WHERE email = :email)")
    suspend fun isEmailExist(email: String): Boolean

    @Query("SELECT EXISTS(SELECT 1 FROM User WHERE username = :username)")
    suspend fun isUsernameExist(username: String): Boolean

    @Query("SELECT COUNT(*)FROM User WHERE username = :username AND password = :password")
    suspend fun checkUser(username: String, password: String): Int

    @Query("UPDATE User SET themeTask = :theme WHERE username = :username")
    suspend fun setTheme(username: String, theme: Int)

    @Query("SELECT themeTask FROM User WHERE id = :id")
    suspend fun getTheme(id: Long): Int

    @Query("SELECT id FROM User WHERE username = :userName")
    suspend fun getId(userName: String): Long

    @Query("SELECT * FROM User WHERE id = :id")
    fun getUserById(id: Long): LiveData<User>
}