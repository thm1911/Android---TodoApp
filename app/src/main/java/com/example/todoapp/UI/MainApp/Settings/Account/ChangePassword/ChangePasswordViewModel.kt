package com.example.todoapp.UI.MainApp.Settings.Account.ChangePassword

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todoapp.Database.User.UserRepository
import com.example.todoapp.Model.User
import com.example.todoapp.UI.ShareViewModel
import kotlinx.coroutines.launch

class ChangePasswordViewModel(shareViewModel: ShareViewModel, context: Context) : ViewModel() {
    private val userRepository: UserRepository
    val listUser: LiveData<User>

    init {
        userRepository = UserRepository(context)
        val userId = shareViewModel.userId
        listUser = userRepository.getUserById(userId)
    }

    fun updatePassword(userId: Long, password: String) = viewModelScope.launch {
        userRepository.updatePassword(userId, password)
    }

    class ChangePasswordViewModelFactory(
        private val shareViewModel: ShareViewModel,
        private val context: Context
    ) : ViewModelProvider
    .Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ChangePasswordViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ChangePasswordViewModel(shareViewModel, context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}