package com.example.todoapp.Adapter.RecyclerViewAdapter

import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.Model.Category
import com.example.todoapp.Model.Task
import com.example.todoapp.R
import java.util.Locale

class CalendarAdapter(private val onClick: (Task) -> Unit) :
    RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {
    private val task = mutableListOf<Task>()
    private var category = emptyList<Category>()

    class CalendarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val time: TextView = view.findViewById(R.id.time)
        val title: TextView = view.findViewById(R.id.title)
        val description: TextView = view.findViewById(R.id.description)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CalendarAdapter.CalendarViewHolder {
        val binding =
            LayoutInflater.from(parent.context).inflate(R.layout.task_calendar_item, parent, false)
        return CalendarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalendarAdapter.CalendarViewHolder, position: Int) {
        val curTask = task[position]

        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val time = timeFormat.format(curTask.dueDate)
        val resultCategory = category.find { curTask.categoryId == it.id }

        holder.time.setText(time)
        holder.title.setText(curTask.title)
        holder.description.setText(curTask.description)
        holder.title.setBackgroundColor(Color.parseColor(resultCategory?.color))
        holder.description.setBackgroundColor(Color.parseColor(resultCategory?.color))
    }

    override fun getItemCount(): Int {
        return task.size
    }

    fun setData(task: List<Task>) {
        val result = DiffUtil.calculateDiff(TaskDiffUtilCallBack(this.task, task))
        this.task.clear()
        this.task.addAll(task)
        return result.dispatchUpdatesTo(this)
    }

    fun setCategory(category: List<Category>) {
        this.category = category
        notifyDataSetChanged()
    }

    class TaskDiffUtilCallBack(private val oldList: List<Task>, private val newList: List<Task>) :
        DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

    }

}