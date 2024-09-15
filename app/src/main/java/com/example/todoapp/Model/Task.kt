package com.example.todoapp.Model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Task",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val userId: Int,
    val title: String,
    val description: String,
    val category: String,
    val isDone: Boolean,
    val createDate: Date,
    val dueDate: Date
)