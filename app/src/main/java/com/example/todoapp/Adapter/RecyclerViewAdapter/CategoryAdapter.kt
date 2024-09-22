package com.example.todoapp.Adapter.RecyclerViewAdapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.Model.Category
import com.example.todoapp.R
import kotlinx.coroutines.currentCoroutineContext

class CategoryAdapter(private val onClick: (Category) -> Unit): RecyclerView.Adapter<CategoryAdapter.CategoryViewHodel>() {
    private val listCategory = mutableListOf<Category>()
    class CategoryViewHodel(view: View): RecyclerView.ViewHolder(view){
        val nameCategory : TextView = view.findViewById(R.id.category)
        val color: View = view.findViewById(R.id.color)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHodel {
        val binding = LayoutInflater.from(parent.context).inflate(R.layout.fragment_category_item, parent, false)
        return CategoryViewHodel(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHodel, position: Int) {
        val curCategory = listCategory[position]

        holder.nameCategory.text = curCategory.name
        val color = curCategory.color
        holder.color.setBackgroundColor(Color.parseColor(color))

        holder.itemView.setOnClickListener {
            onClick(curCategory)
        }

    }

    override fun getItemCount(): Int {
        return listCategory.size
    }

    fun setData(category: List<Category>){
        val result = DiffUtil.calculateDiff(CategoryDiffUtilCallBack(this.listCategory, category))
        this.listCategory.clear()
        this.listCategory.addAll(category)
        result.dispatchUpdatesTo(this)
    }

    class CategoryDiffUtilCallBack(private val oldList: List<Category>, private val newList: List<Category>): DiffUtil.Callback(){
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