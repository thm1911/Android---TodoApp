package com.example.todoapp.Database.User

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.Model.User

@Dao
interface UserDAO {
    @Insert
    suspend fun insertUser(user: User)

    @Query("UPDATE User SET password = :newPassword WHERE id = :id")
    fun updatePassword(id: Int, newPassword: String)

    @Query("SELECT EXISTS(SELECT 1 FROM User WHERE email = :email)")
    suspend fun isEmailExist(email: String): Boolean

    @Query("SELECT EXISTS(SELECT 1 FROM User WHERE username = :username)")
    suspend fun isUsernameExist(username: String): Boolean

    @Query("SELECT COUNT(*)FROM User WHERE username = :username AND password = :password")
    suspend fun checkUser(username: String, password: String): Int

    @Query("UPDATE User SET themeTask = :theme WHERE username = :username")
    suspend fun setTheme(username: String, theme: Int)

    @Query("SELECT themeTask FROM User WHERE id = :id")
    suspend fun getTheme(id: Int): Int
}