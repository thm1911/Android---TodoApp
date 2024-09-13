package com.example.todoapp.UI.SignUp.User

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todoapp.Database.User.UserRepository
import com.example.todoapp.Model.User
import kotlinx.coroutines.launch

class UserSignUpViewModel(context: Context) : ViewModel() {
    private val userRepository: UserRepository

    init {
        userRepository = UserRepository(context)
    }

    fun isUserExist(user: String, callback: (Boolean) -> Unit) = viewModelScope.launch {
        val check = userRepository.isUsernameExist(user)
        callback(check)
    }

    fun insertUser(user: User) = viewModelScope.launch {
        userRepository.insertUser(user)
    }

    class UserSignUpViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserSignUpViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return UserSignUpViewModel(context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}