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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.todoapp.Adapter.RecyclerViewAdapter.CategoryAdapter
import com.example.todoapp.Adapter.RecyclerViewAdapter.TaskAdapter
import com.example.todoapp.R
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

        initCategoryList()
        initTaskList()

        binding.viewAllCategory.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCategoryHomeFragment())
            bottom_nav.visibility = View.GONE
        }

        binding.viewAllTask.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToTaskHomeFragment(-1, "home"))
            bottom_nav.visibility = View.GONE
        }
    }

    private fun initCategoryList(){
        val categoryRecyclerView = binding.categoryRecyclerView
        categoryRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val adapter = CategoryAdapter{

        }
        categoryRecyclerView.adapter = adapter
        val space = resources.getDimensionPixelSize(R.dimen.space)
        categoryRecyclerView.addItemDecoration(SpacingItem(space))
        viewModel.listCategoryAndTask.observe(viewLifecycleOwner){category ->
            adapter.setData(category)
            adapter.showAllCategory(false)
        }
    }

    private fun initTaskList(){
        val taskRecyclerView = binding.taskRecyclerView
        taskRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        val adapter = TaskAdapter{

        }
        taskRecyclerView.adapter = adapter
        val space = resources.getDimensionPixelSize(R.dimen.space)
        taskRecyclerView.addItemDecoration(SpacingItem(space))
        viewModel.listTask.observe(viewLifecycleOwner){task ->
            adapter.showAllTask(false)
            adapter.setData(task)
        }

        viewModel.getTheme(shareViewModel.userId){theme ->
            adapter.setTheme(theme)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}