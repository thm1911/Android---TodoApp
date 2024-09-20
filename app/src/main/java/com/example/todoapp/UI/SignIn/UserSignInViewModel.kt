package com.example.todoapp.UI.SignIn

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todoapp.Database.User.UserRepository
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class UserSignInViewModel(context: Context) : ViewModel() {
    private val userRepository: UserRepository

    init {
        userRepository = UserRepository(context)
    }

    fun checkUser(username: String, password: String, callback: (Boolean) -> Unit) =
        viewModelScope.launch {
            val check = userRepository.checkUser(username, password)
            callback(check)
        }

    fun getId(username: String, callback: (Long) -> Unit) = viewModelScope.launch {
        val id = userRepository.getId(username)
        callback(id)
    }

    class UserSignInViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserSignInViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return UserSignInViewModel(context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}