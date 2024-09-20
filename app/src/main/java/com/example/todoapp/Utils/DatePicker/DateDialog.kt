package com.example.todoapp.Utils.DatePicker

import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.widget.DatePicker
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Locale

class DateDialog {
    companion object{
        fun selectDate(context: Context, view: TextView){
            val calendar =  Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                context,{
                        _: DatePicker, selectYear: Int, selectMonth: Int, selectDay: Int ->
                    val date = formatDate(selectYear, selectMonth, selectDay)
                    view.setText(date)
                },
                year,
                month,
                day,
            )

            return datePickerDialog.show()
        }

        private fun formatDate(year: Int, month: Int, day: Int): String{
            val calendar = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, day)
            }
            val date = SimpleDateFormat("EE, dd MMM yyyy", Locale.getDefault())
            return date.format(calendar.time)
        }
    }
}