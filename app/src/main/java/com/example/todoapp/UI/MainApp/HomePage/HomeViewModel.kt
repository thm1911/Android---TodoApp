package com.example.todoapp.UI.MainApp.HomePage

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todoapp.Database.Category.CategoryRepository
import com.example.todoapp.Database.Task.TaskRepository
import com.example.todoapp.Database.User.UserRepository
import com.example.todoapp.Model.CategoryAndTask
import com.example.todoapp.Model.Task
import com.example.todoapp.UI.ShareViewModel
import kotlinx.coroutines.launch

class HomeViewModel(shareViewModel: ShareViewModel, context: Context): ViewModel() {
    private val taskRepository: TaskRepository
    private val categoryRepository: CategoryRepository
    private val userRepository: UserRepository
    val listCategoryAndTask: LiveData<List<CategoryAndTask>>
    val listTask: LiveData<List<Task>>
    init {
        taskRepository = TaskRepository(context)
        categoryRepository = CategoryRepository(context)
        userRepository = UserRepository(context)
        val userId = shareViewModel.userId
        listCategoryAndTask = categoryRepository.getAllCategoryAndTask(userId)
        listTask = taskRepository.getAllTask(userId)
    }

    fun getTheme(id: Long, callBack: (Int) -> Unit) = viewModelScope.launch {
        val theme = userRepository.getTheme(id)
        callBack(theme)
    }

    class HomeViewModelFactory(private val shareViewModel: ShareViewModel, private val context: Context): ViewModelProvider
    .Factory{
            override fun <T: ViewModel> create (modelClass: Class<T>): T{
                if(modelClass.isAssignableFrom(HomeViewModel::class.java)){
                    @Suppress("UNCHECKED_CAST")
                    return HomeViewModel(shareViewModel, context) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }

}