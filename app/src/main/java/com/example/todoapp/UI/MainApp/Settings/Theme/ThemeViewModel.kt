package com.example.todoapp.UI.MainApp.Settings.Theme

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todoapp.Database.User.UserRepository
import com.example.todoapp.Model.User
import com.example.todoapp.UI.ShareViewModel
import kotlinx.coroutines.launch

class ThemeViewModel(shareViewModel: ShareViewModel, context: Context): ViewModel() {
    private val userRepository: UserRepository
    val listUser: LiveData<User>

    init {
        userRepository = UserRepository(context)
        val userId = shareViewModel.userId
        listUser = userRepository.getUserById(userId)
    }

    fun setTheme(usename: String, theme: Int) = viewModelScope.launch {
        userRepository.setTheme(usename, theme)
    }
    class ThemeViewModelFactory(private val shareViewModel: ShareViewModel, private val context: Context): ViewModelProvider
    .Factory{
            override fun <T: ViewModel> create (modelClass: Class<T>): T{
                if(modelClass.isAssignableFrom(ThemeViewModel::class.java)){
                    @Suppress("UNCHECKED_CAST")
                    return ThemeViewModel(shareViewModel, context) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
}