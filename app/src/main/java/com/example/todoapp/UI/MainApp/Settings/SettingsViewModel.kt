package com.example.todoapp.UI.MainApp.Settings

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.Database.User.UserRepository
import com.example.todoapp.Model.User
import com.example.todoapp.UI.ShareViewModel

class SettingsViewModel(shareViewModel: ShareViewModel, context: Context) : ViewModel() {
    private val userRepository: UserRepository
    val listUser: LiveData<User>

    init {
        userRepository = UserRepository(context)
        val userId = shareViewModel.userId
        listUser = userRepository.getUserById(userId)
    }

    class SettingsViewModelFactory(
        private val shareViewModel: ShareViewModel,
        private val context: Context
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SettingsViewModel(shareViewModel, context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}