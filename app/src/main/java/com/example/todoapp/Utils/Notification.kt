package com.example.todoapp.Utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.view.ContentInfoCompat.Flags
import com.example.todoapp.Model.Task
import com.example.todoapp.UI.AlarmReceiver

class Notification {
    companion object{

        fun notificationTask(context: Context, task: Task){
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val timeNotify = task.dueDate.time

            val intent = Intent(context, AlarmReceiver::class.java).apply {
                putExtra("title", task.title)
                putExtra("description", task.description)
                putExtra("id", task.id)
            }

            val pendingIntent = PendingIntent.getBroadcast(context, task.id.toInt(), intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timeNotify, pendingIntent)
        }

    }
}