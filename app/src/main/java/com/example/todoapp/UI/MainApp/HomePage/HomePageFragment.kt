package com.example.todoapp.UI.MainApp.HomePage

import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.Adapter.RecyclerViewAdapter.TaskAdapter
import com.example.todoapp.R
import com.example.todoapp.UI.MainApp.HomePage.CreateTask.BottomCreateTaskFragment
import com.example.todoapp.UI.ShareViewModel
import com.example.todoapp.UI.SpacingItem
import com.example.todoapp.databinding.FragmentHomePageBinding

class HomePageFragment : Fragment() {
    private var _binding: FragmentHomePageBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomePageViewModel by viewModels(){
        HomePageViewModel.HomePageViewModelFactory(shareViewModel, requireContext())
    }
    private val shareViewModel: ShareViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomePageBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = binding.recyclerView
        val adapter = TaskAdapter{ task ->
            val id = task.id
            findNavController().navigate(HomePageFragmentDirections.actionHomePageFragmentToTaskDetailFragment(id))
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        val space = resources.getDimensionPixelSize(R.dimen.space)
        recyclerView.addItemDecoration(SpacingItem(space))
        viewModel.listTask.observe(viewLifecycleOwner){task ->
            adapter.setData(task)
        }

        viewModel.getTheme(shareViewModel.userId){theme ->
            adapter.setTheme(theme)
        }

        binding.createTask.setOnClickListener {
            showBottomSheet()
        }
    }

    private fun showBottomSheet(){
        val bottomCreateTaskFragment = BottomCreateTaskFragment()
        bottomCreateTaskFragment.show(childFragmentManager, bottomCreateTaskFragment.tag)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}