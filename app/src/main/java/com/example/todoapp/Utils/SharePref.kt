package com.example.todoapp.Utils

import android.app.Application
import android.content.Context

class SharePref {
    companion object{
        fun getUserIdFromPreferences(application: Application): Long{
            val sharePref = application.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            return sharePref.getLong("user_id", -1L)
        }

        fun setUserIdToPreferences(application: Application, userId: Long){
            val sharePref = application.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            with(sharePref.edit()){
                putLong("user_id", userId)
                apply()
            }
        }

        fun getTaskIdFromPreferences(application: Application): Long{
            val sharePref = application.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            return sharePref.getLong("task_id", -1L)
        }

        fun setTaskIdToPreferences(application: Application, taskId: Long){
            val sharePref = application.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            with(sharePref.edit()){
                putLong("user_id", taskId)
                apply()
            }
        }

        fun getIsUserSignIn(application: Application): Boolean{
            val sharePref = application.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            return sharePref.getBoolean("is_logged_in", false)
        }

        fun setUserLoginState(application: Application, userId: Long, isLoggedIn: Boolean) {
            val sharePref = application.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            with(sharePref.edit()) {
                putLong("user_id", userId)
                putBoolean("is_logged_in", isLoggedIn)
                apply()
            }
        }
    }
}