package com.example.todoapp.Adapter.RecyclerViewAdapter

import android.graphics.drawable.GradientDrawable
import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.Model.Task
import com.example.todoapp.R
import java.util.Locale

class TaskAdapter(private val onClick: (Task) -> Unit) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    private val tasks = mutableListOf<Task>()
    private var themeTask: Int = 0
    private var showAll: Boolean = true

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val theme: View = view.findViewById(R.id.theme)
        val title: TextView = view.findViewById(R.id.title)
        val time: TextView = view.findViewById(R.id.time)
        val date: TextView = view.findViewById(R.id.date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding =
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_task_item, parent, false)
        return TaskViewHolder(binding)
    }

    override fun getItemCount(): Int {
        if (showAll) return tasks.size
        else return minOf(tasks.size, 3)
    }

    fun showAllTask(check: Boolean) {
        showAll = check
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val curTask = tasks[position]
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val dateFormat = SimpleDateFormat("EE, dd MMM yyyy", Locale.getDefault())
        holder.title.text = curTask.title
        holder.time.text = timeFormat.format(curTask.dueDate)
        holder.date.text = dateFormat.format(curTask.dueDate)

        holder.theme.setBackgroundColor(themeTask)

        holder.itemView.setOnClickListener {
            onClick(curTask)
        }
    }

    fun setTheme(theme: Int) {
        themeTask = theme
        notifyDataSetChanged()
    }

    fun setData(tasks: List<Task>) {
        val result = DiffUtil.calculateDiff(TaskDiffUtilCallBack(this.tasks, tasks))
        this.tasks.clear()
        this.tasks.addAll(tasks)
        result.dispatchUpdatesTo(this)
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