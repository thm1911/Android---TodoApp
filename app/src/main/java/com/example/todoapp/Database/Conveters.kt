package com.example.todoapp.Database

import androidx.room.TypeConverter
import java.util.Date

class Conveters {
    @TypeConverter
    fun timestampToDate(value: Long?): Date?{
        return value?.let { Date(it) }
    }
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long?{
        return date?.time
    }
}