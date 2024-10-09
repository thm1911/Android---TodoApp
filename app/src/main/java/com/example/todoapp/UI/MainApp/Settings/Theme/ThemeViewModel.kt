package com.example.todoapp.UI.MainApp.Settings.Theme

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todoapp.Database.User.UserRepository
import com.example.todoapp.Model.User
import com.example.todoapp.Utils.SharePref
import kotlinx.coroutines.launch

class ThemeViewModel(application: Application): ViewModel() {
    private val userRepository: UserRepository
    val listUser: LiveData<User>

    init {
        userRepository = UserRepository(application)
        val userId = SharePref.getUserIdFromPreferences(application)
        listUser = userRepository.getUserById(userId)
    }

    fun setTheme(usename: String, theme: Int) = viewModelScope.launch {
        userRepository.setTheme(usename, theme)
    }
    class ThemeViewModelFactory(private val application: Application): ViewModelProvider
    .Factory{
            override fun <T: ViewModel> create (modelClass: Class<T>): T{
                if(modelClass.isAssignableFrom(ThemeViewModel::class.java)){
                    @Suppress("UNCHECKED_CAST")
                    return ThemeViewModel(application) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
}