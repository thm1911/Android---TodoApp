package com.example.todoapp.UI.MainApp.HomePage.Category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.todoapp.Adapter.RecyclerViewAdapter.CategoryAdapter
import com.example.todoapp.R
import com.example.todoapp.UI.MainApp.HomePage.Category.CreateCategory.BottomCreateCategoryFragment
import com.example.todoapp.UI.SpacingItem
import com.example.todoapp.databinding.FragmentCategoryHomeBinding

class CategoryHomeFragment : Fragment() {
    private var _binding: FragmentCategoryHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CategoryHomeViewModel by viewModels {
        CategoryHomeViewModel.CategoryHomeViewModelFactory(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoryHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListCategory()

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.createCategory.setOnClickListener {
            val bottomCreateCategory = BottomCreateCategoryFragment()
            bottomCreateCategory.show(childFragmentManager, bottomCreateCategory.tag)
        }
    }

    private fun initListCategory() {
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        val adapter = CategoryAdapter { categoryAndTask ->
            val id = categoryAndTask.id
            findNavController().navigate(
                CategoryHomeFragmentDirections.actionCategoryHomeFragmentToTaskHomeFragment(
                    id,
                    "category"
                )
            )
        }
        recyclerView.adapter = adapter
        val space = resources.getDimensionPixelSize(R.dimen.space)
        recyclerView.addItemDecoration(SpacingItem(space))

        viewModel.listCategory.observe(viewLifecycleOwner) { category ->
            adapter.setData(category)
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

    private fun filterList(query: String?) {
        val list = viewModel.listCategory.value ?: emptyList()
        if (query.isNullOrEmpty()) {
            (binding.recyclerView.adapter as CategoryAdapter).setData(list)
        } else {
            val filterList = list.filter { category ->
                category.nameCategory.lowercase().contains(query.lowercase())
            }
            (binding.recyclerView.adapter as CategoryAdapter).setData(filterList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}