package com.example.todoapp.UI.MainApp.HomePage

import android.content.Context
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todoapp.Adapter.RecyclerViewAdapter.TaskAdapter
import com.example.todoapp.Database.Category.CategoryRepository
import com.example.todoapp.Database.Task.TaskRepository
import com.example.todoapp.Database.User.UserRepository
import com.example.todoapp.Model.Task
import com.example.todoapp.UI.ShareViewModel
import kotlinx.coroutines.launch

class HomePageViewModel(shareViewModel: ShareViewModel, context: Context): ViewModel() {
    private val taskRepository: TaskRepository
    private val userRepository: UserRepository
    val listTask: LiveData<List<Task>>
    init{
        taskRepository = TaskRepository(context)
        userRepository = UserRepository(context)
        val userId = shareViewModel.userId
        listTask = taskRepository.getAllTask(userId)
    }

    fun getTheme(id: Long, callBack: (Int) -> Unit) = viewModelScope.launch {
        val theme = userRepository.getTheme(id)
        callBack(theme)
    }

    class HomePageViewModelFactory(private val shareViewModel: ShareViewModel, private val context: Context): ViewModelProvider
    .Factory{
            override fun <T: ViewModel> create (modelClass: Class<T>): T{
                if(modelClass.isAssignableFrom(HomePageViewModel::class.java)){
                    @Suppress("UNCHECKED_CAST")
                    return HomePageViewModel(shareViewModel, context) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
}