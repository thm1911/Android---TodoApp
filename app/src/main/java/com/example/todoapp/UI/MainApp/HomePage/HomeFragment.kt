package com.example.todoapp.UI.MainApp.HomePage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.todoapp.Adapter.RecyclerViewAdapter.CategoryAdapter
import com.example.todoapp.Adapter.RecyclerViewAdapter.TaskAdapter
import com.example.todoapp.R
import com.example.todoapp.UI.MainApp.HomePage.HomeFragmentDirections
import com.example.todoapp.UI.ShareViewModel
import com.example.todoapp.UI.SpacingItem
import com.example.todoapp.databinding.FragmentHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels {
        HomeViewModel.HomeViewModelFactory(shareViewModel, requireContext())
    }
    private val shareViewModel: ShareViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottom_nav = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottom_nav.visibility = View.VISIBLE

        initUser()
        initCategoryList(bottom_nav)
        initTaskList(bottom_nav)

        binding.viewAllCategory.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCategoryHomeFragment())
            bottom_nav.visibility = View.GONE
        }

        binding.viewAllTask.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToTaskHomeFragment(
                    -1,
                    "home"
                )
            )
            bottom_nav.visibility = View.GONE
        }
    }

    private fun initUser() {
        viewModel.listUser.observe(viewLifecycleOwner) { user ->
            val username = user.username
            val email = user.email

            binding.username.setText(username)
            binding.email.setText(email)
        }
    }

    private fun initCategoryList(bottom_nav: BottomNavigationView) {
        val categoryRecyclerView = binding.categoryRecyclerView
        categoryRecyclerView.layoutManager =
            GridLayoutManager(requireContext(), 2)

        val adapter = CategoryAdapter { categoryAndTask ->
            val id = categoryAndTask.id
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToTaskHomeFragment(
                    id,
                    "category"
                )
            )
            bottom_nav.visibility = View.GONE
        }
        categoryRecyclerView.adapter = adapter
        val space = resources.getDimensionPixelSize(R.dimen.space)
        categoryRecyclerView.addItemDecoration(SpacingItem(space))
        viewModel.listCategoryAndTask.observe(viewLifecycleOwner) { category ->
            adapter.setData(category)
            adapter.showAllCategory(false)
            binding.category.setText(String.format("Category(%d)", category.size))
        }
    }

    private fun initTaskList(bottom_nav: BottomNavigationView) {
        val taskRecyclerView = binding.taskRecyclerView
        taskRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        val adapter = TaskAdapter { task ->
            val id = task.id
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToTaskDetailFragment(
                    id,
                    "taskId"
                )
            )
            bottom_nav.visibility = View.GONE
        }
        taskRecyclerView.adapter = adapter
        val space = resources.getDimensionPixelSize(R.dimen.space)
        taskRecyclerView.addItemDecoration(SpacingItem(space))
        viewModel.listTask.observe(viewLifecycleOwner) { task ->
            adapter.showAllTask(false)
            adapter.setData(task)
            binding.task.setText(String.format("Task(%d)", task.size))
        }

        viewModel.getTheme(shareViewModel.userId) { theme ->
            adapter.setTheme(theme)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}