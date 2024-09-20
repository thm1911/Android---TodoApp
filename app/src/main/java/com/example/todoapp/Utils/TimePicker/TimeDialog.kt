package com.example.todoapp.Utils.TimePicker

import android.app.TimePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.widget.TextView
import android.widget.TimePicker
import java.text.SimpleDateFormat
import java.util.Locale

class TimeDialog {
    companion object{
        fun selectTime(context: Context, view: TextView) {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(
                context,{
                        _: TimePicker, selectHour: Int, selectMinute: Int ->
                    val time = formatTime(selectHour, selectMinute)
                    view.setText(time)
                },
                hour,
                minute,
                true
            )
            return timePickerDialog.show()
        }

        private fun formatTime(hour: Int, minute: Int): String{
            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
            }
            val time = SimpleDateFormat("HH:mm", Locale.getDefault())
            return time.format(calendar.time)
        }
    }
}