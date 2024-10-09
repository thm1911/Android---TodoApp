package com.example.todoapp.Database.User

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import com.example.todoapp.Database.TodoDatabase
import com.example.todoapp.Model.User

class UserRepository(application: Application) {
    private val userDAO: UserDAO


    init {
        userDAO = TodoDatabase.getInstance(application).userDAO()
    }

    suspend fun insertUser(user: User) = userDAO.insertUser(user)
    suspend fun updatePassword(id: Long, newPass: String) = userDAO.updatePassword(id, newPass)
    suspend fun isEmailExist(email: String) = userDAO.isEmailExist(email)
    suspend fun isUsernameExist(username: String) = userDAO.isUsernameExist(username)
    suspend fun checkUser(username: String, password: String): Boolean {
        val check = userDAO.checkUser(username, password)
        return check > 0
    }

    suspend fun setTheme(username: String, theme: Int) = userDAO.setTheme(username, theme)
    suspend fun getTheme(id: Long) = userDAO.getTheme(id)
    suspend fun getId(userName: String) = userDAO.getId(userName)
    fun getUserById(id: Long): LiveData<User> = userDAO.getUserById(id)
}