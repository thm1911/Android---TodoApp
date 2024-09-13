package com.example.todoapp.UI.SignUp.Email

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.todoapp.Database.User.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EmailSignUpViewModel(context: Context) : ViewModel() {
    private val userRepository: UserRepository

    init {
        userRepository = UserRepository(context)
    }

    fun isEmailExist(email: String, callback: (Boolean) -> Unit) = viewModelScope.launch {
        val exist = userRepository.isEmailExist(email)
        callback(exist)
    }

    class EmailSignUpViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EmailSignUpViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return EmailSignUpViewModel(context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}