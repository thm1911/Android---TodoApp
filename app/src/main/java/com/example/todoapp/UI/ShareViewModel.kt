package com.example.todoapp.UI

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ShareViewModel: ViewModel() {
    var userId: Long = 0
    var taskId: Long = 0
    var isLinearLayout: Boolean = true
}