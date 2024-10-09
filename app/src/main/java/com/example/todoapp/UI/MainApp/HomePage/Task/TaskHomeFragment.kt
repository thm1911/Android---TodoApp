package com.example.todoapp.UI.MainApp.HomePage.Task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.Adapter.RecyclerViewAdapter.TaskAdapter
import com.example.todoapp.Model.Category
import com.example.todoapp.R
import com.example.todoapp.UI.MainApp.HomePage.Category.CreateCategory.BottomCreateCategoryFragment
import com.example.todoapp.UI.SpacingItem
import com.example.todoapp.Utils.SharePref
import com.example.todoapp.databinding.FragmentTaskHomeBinding

class TaskHomeFragment : Fragment() {
    private var _binding: FragmentTaskHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var gridLayoutManager: GridLayoutManager
    private var isLinearLayout: Boolean = true
    private val viewModel: TaskHomeViewModel by viewModels {
        TaskHomeViewModel.TaskHomeViewModelFactory(requireActivity().application)
    }
    private val args: TaskHomeFragmentArgs by navArgs()
    private val custom = listOf("No Sort", "Sort Date A-Z", "Sort Date Z-A")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        customList()

        binding.createTask.setOnClickListener {
            findNavController().navigate(
                TaskHomeFragmentDirections.actionTaskHomeFragmentToCreateTaskFragment(
                    0L,
                    "taskId"
                )
            )
        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initRecyclerView() {
        val id = args.categoryId
        val mes = args.mes
        val recyclerView = binding.recyclerView
        val adapter = TaskAdapter { task ->
            val id = task.id
            findNavController().navigate(
                TaskHomeFragmentDirections.actionTaskHomeFragmentToTaskDetailFragment(
                    id,
                    "taskId"
                )
            )
        }

        linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        gridLayoutManager = GridLayoutManager(requireContext(), 2)

        if (isLinearLayout) recyclerView.layoutManager = linearLayoutManager
        else recyclerView.layoutManager = gridLayoutManager

        recyclerView.adapter = adapter
        val space = resources.getDimensionPixelSize(R.dimen.space)
        recyclerView.addItemDecoration(SpacingItem(space))
        if (mes == "home") {
            viewModel.listTask.observe(viewLifecycleOwner) { task ->
                adapter.setData(task)
            }
        } else if (mes == "category") {
            update(id)
            var count: Int = 0
            viewModel.listTask = viewModel.getTaskByCategory(id, SharePref.getUserIdFromPreferences(requireActivity().application))
            viewModel.listTask.observe(viewLifecycleOwner) { task ->
                count = task.size
                adapter.setData(task)
            }
            binding.deleteCategory.setOnClickListener {
                if (count == 0) showDialog("Are you sure you want to delete Category", true)
                else showDialog("You cannot delete this category", false)
            }
        }

        viewModel.getTheme(SharePref.getUserIdFromPreferences(requireActivity().application)) { theme ->
            adapter.setTheme(theme)
        }

        binding.layout.setOnClickListener {
            isLinearLayout = !isLinearLayout
            if (isLinearLayout) {
                recyclerView.layoutManager = linearLayoutManager
                binding.layout.setBackgroundResource(R.drawable.ic_linear_layout)
            } else {
                recyclerView.layoutManager = gridLayoutManager
                binding.layout.setBackgroundResource(R.drawable.ic_grid_layout)
            }
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })

    }

    private fun update(id: Long) {
        binding.createTask.visibility = View.INVISIBLE
        viewModel.getCategoryById(id).observe(viewLifecycleOwner) { category ->
            binding.text.setText(category.name)
        }
        binding.deleteCategory.visibility = View.VISIBLE
    }

    private fun showDialog(mes: String, check: Boolean) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete Category")
        builder.setMessage(mes)
        builder.setNegativeButton("OK") { dialog, which ->
            if (check) {
                var name: String = ""
                var color: String = ""
                viewModel.getCategoryById(args.categoryId).observe(viewLifecycleOwner) { category ->
                    name = category.name
                    color = category.color
                }
                viewModel.delete(Category(args.categoryId, SharePref.getUserIdFromPreferences(requireActivity().application), name, color))
                findNavController().popBackStack()
            } else dialog.dismiss()
        }
        if (check) {
            builder.setPositiveButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun filterList(query: String?) {
        val list = viewModel.listTask.value ?: emptyList()

        if (query.isNullOrEmpty()) {
            (binding.recyclerView.adapter as TaskAdapter).setData(list)
        } else {
            val filterList = list.filter { task ->
                task.title.lowercase().contains(query.lowercase())
            }
            (binding.recyclerView.adapter as TaskAdapter).setData(filterList)
        }
    }

    private fun customList(){
        val customAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, custom)
        customAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = customAdapter
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(position){
                    0 ->{
                        viewModel.listTask.observe(viewLifecycleOwner){task ->
                            (binding.recyclerView.adapter as TaskAdapter).setData(task)
                        }
                    }

                    1 -> {
                        viewModel.listTask.observe(viewLifecycleOwner){task ->
                            val sortedList = task.sortedBy { it.dueDate }
                            (binding.recyclerView.adapter as TaskAdapter).setData(sortedList)
                        }
                    }

                    2 ->{
                        viewModel.listTask.observe(viewLifecycleOwner){task ->
                            val sortedList = task.sortedByDescending{ it.dueDate }
                            (binding.recyclerView.adapter as TaskAdapter).setData(sortedList)
                        }
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}