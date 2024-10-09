package com.example.todoapp.UI.MainApp.ChooseTheme

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todoapp.Database.TodoDatabase
import com.example.todoapp.Database.User.UserRepository
import kotlinx.coroutines.launch

class HomepageChooseThemeViewModel(application: Application) : ViewModel() {
    private val userRepository: UserRepository

    init {
        userRepository = UserRepository(application)
    }

    fun setTheme(username: String, theme: Int) = viewModelScope.launch {
        userRepository.setTheme(username, theme)
    }

    class HomepageChooseThemeViewModelFactory(private val application: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomepageChooseThemeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HomepageChooseThemeViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}