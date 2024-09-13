package com.example.todoapp.Database.User

import android.content.Context
import com.example.todoapp.Database.TodoDatabase
import com.example.todoapp.Model.User

class UserRepository(context: Context) {
    private val userDAO: UserDAO

    init {
        userDAO = TodoDatabase.getInstance(context).userDAO()
    }

    suspend fun insertUser(user: User) = userDAO.insertUser(user)
    fun updatePassword(id: Int, newPass: String) = userDAO.updatePassword(id, newPass)
    suspend fun isEmailExist(email: String) = userDAO.isEmailExist(email)
    suspend fun isUsernameExist(username: String) = userDAO.isUsernameExist(username)
    suspend fun checkUser(username: String, password: String): Boolean {
        val check = userDAO.checkUser(username, password)
        return check > 0
    }
}