package com.example.todoapp.UI

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import com.example.todoapp.R
import com.example.todoapp.UI.MainApp.HomePage.Task.TaskDetail.TaskDetailFragment

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val id = intent.getLongExtra("id", -1)

        val pendingIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.main_nav_graph)
            .setDestination(R.id.taskDetailFragment)
            .setArguments(Bundle().apply {
                putLong("id", id)
                putString("mes", "taskId")})
            .createTaskStackBuilder()
            .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(context, "Noteapp")
            .setSmallIcon(R.drawable.ic_clock)
            .setContentTitle(title)
            .setContentText(description)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(false)
            .setContentIntent(pendingIntent)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(1, builder.build())

    }
}