package com.example.todoapp.UI.MainApp.HomePage.Category.ChooseCategory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.Adapter.RecyclerViewAdapter.CategoryAdapter
import com.example.todoapp.R
import com.example.todoapp.UI.MainApp.HomePage.Category.CreateCategory.BottomCreateCategoryFragment
import com.example.todoapp.UI.ShareViewModel
import com.example.todoapp.UI.SpacingItem
import com.example.todoapp.databinding.FragmentChooseCategoryBinding

class ChooseCategoryFragment : Fragment() {
    private var _binding: FragmentChooseCategoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ChooseCategoryViewModel by viewModels {
        ChooseCategoryViewModel.ChooseCategoryViewModelFactory(shareViewModel, requireContext())
    }
    private val shareViewModel: ShareViewModel by activityViewModels()
    private val args: ChooseCategoryFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChooseCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.add.setOnClickListener {
            val bottomCreateCategory = BottomCreateCategoryFragment()
            bottomCreateCategory.show(childFragmentManager, bottomCreateCategory.tag)
        }

        val recyclerView = binding.containerCategory
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        val navOption1 = NavOptions.Builder().setPopUpTo(R.id.categoryFragment, true)
            .setPopUpTo(R.id.createTaskFragment, true)
            .build()
        val navOption2 = NavOptions.Builder().setPopUpTo(R.id.categoryFragment, true)
            .setPopUpTo(R.id.taskDetailFragment, true)
            .build()

        val adapter = CategoryAdapter{category ->
            val categoryId = category.id
            val mes = args.mes

            if (mes == "Create Task"){
                findNavController().navigate(ChooseCategoryFragmentDirections.actionCategoryFragmentToCreateTaskFragment(categoryId, "categoryId"), navOption1)
            }

            else if (mes == "Task Detail"){
                findNavController().navigate(ChooseCategoryFragmentDirections.actionCategoryFragmentToTaskDetailFragment(categoryId, "categoryId"), navOption2)
            }
        }
        recyclerView.adapter = adapter
        val space = resources.getDimensionPixelSize(R.dimen.space)
        recyclerView.addItemDecoration(SpacingItem(space))

        viewModel.listCategoryAndTask.observe(viewLifecycleOwner){category ->
            adapter.setData(category)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
