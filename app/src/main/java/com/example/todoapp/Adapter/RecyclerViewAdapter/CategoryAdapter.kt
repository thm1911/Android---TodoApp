package com.example.todoapp.Adapter.RecyclerViewAdapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.Model.CategoryAndTask
import com.example.todoapp.R

class CategoryAdapter(private val onClick: (CategoryAndTask) -> Unit) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHodel>() {
    private val listCategoryAndTask = mutableListOf<CategoryAndTask>()
    private var showAll: Boolean = true

    class CategoryViewHodel(view: View) : RecyclerView.ViewHolder(view) {
        val nameCategory: TextView = view.findViewById(R.id.name)
        val color: View = view.findViewById(R.id.color)
        val totalTask: TextView = view.findViewById(R.id.count)
        val progress: TextView = view.findViewById(R.id.progress)
        val progressBar: ProgressBar = view.findViewById(R.id.progressBar)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHodel {
        val binding =
            LayoutInflater.from(parent.context).inflate(R.layout.category_item_grid_layout, parent, false)
        return CategoryViewHodel(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHodel, position: Int) {
        val curCategory = listCategoryAndTask[position]

        holder.nameCategory.text = curCategory.nameCategory
        val color = curCategory.color
        holder.color.setBackgroundColor(Color.parseColor(color))
        holder.totalTask.text = "${curCategory.totalTask} Tasks"
        val progress = (curCategory.taskIsDone.toFloat() / curCategory.totalTask) * 100
        holder.progress.setText(String.format("%d%%", progress.toInt()))
        holder.progressBar.setProgress(progress.toInt())

        holder.itemView.setOnClickListener {
            onClick(curCategory)
        }
    }

    fun showAllCategory(check: Boolean) {
        showAll = check
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        if (showAll) return listCategoryAndTask.size
        else return minOf(listCategoryAndTask.size, 2)
    }

    fun setData(category: List<CategoryAndTask>) {
        this.listCategoryAndTask.clear()
        this.listCategoryAndTask.addAll(category)
        notifyDataSetChanged()
    }
}