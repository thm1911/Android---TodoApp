package com.example.todoapp.UI.MainApp.HomePage.Task.CreateTask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapp.Model.Task
import com.example.todoapp.R
import com.example.todoapp.UI.ShareViewModel
import com.example.todoapp.Utils.DatePicker.DateDialog
import com.example.todoapp.Utils.TimePicker.TimeDialog
import com.example.todoapp.databinding.FragmentCreateTaskBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CreateTaskFragment : Fragment() {
    private var _binding: FragmentCreateTaskBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CreateTaskViewModel by viewModels() {
        CreateTaskViewModel.CreateTaskViewModelFactory(requireContext())
    }
    private val shareViewModel: ShareViewModel by activityViewModels()
    private val args: CreateTaskFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mes = args.mes
        val categoryId = args.id
        if (categoryId != 0L && mes == "categoryId") {
            viewModel.getCategoryById(categoryId).observe(viewLifecycleOwner) { category ->
                val name = category.name
                binding.categoryText.setText(name)
                binding.categoryText.setTextColor(resources.getColor(R.color.black))
            }
        }

        binding.close.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.time.setOnClickListener {
            TimeDialog.selectTime(requireContext(), binding.timeText)
            binding.timeText.setTextColor(resources.getColor(R.color.black))
        }

        binding.timeText.setOnClickListener {
            TimeDialog.selectTime(requireContext(), binding.timeText)
            binding.timeText.setTextColor(resources.getColor(R.color.black))
        }

        binding.date.setOnClickListener {
            DateDialog.selectDate(requireContext(), binding.dateText)
            binding.dateText.setTextColor(resources.getColor(R.color.black))
        }

        binding.dateText.setOnClickListener {
            DateDialog.selectDate(requireContext(), binding.dateText)
            binding.dateText.setTextColor(resources.getColor(R.color.black))
        }

        binding.category.setOnClickListener {
            selectCategory()
        }

        binding.categoryText.setOnClickListener {
            selectCategory()
        }

        binding.enter.setOnClickListener {
            insertTask(categoryId)
        }
    }


    private fun selectCategory() {
        findNavController().navigate(
            CreateTaskFragmentDirections.actionCreateTaskFragmentToCategoryFragment(
                "Create Task"
            )
        )
    }

    private fun insertTask(categoryId: Long) {
        val title = binding.title.text.toString()
        val description = binding.description.text.toString()
        val timeText = binding.timeText.text.toString()
        val dateText = binding.dateText.text.toString()
        val categoryText = binding.categoryText.text.toString()

        if (title.isEmpty() || description.isEmpty() || timeText == "Add Time" || dateText == "Add Date" || categoryText == "Add Category") {
            binding.warningText.visibility = View.VISIBLE
        } else {
            binding.warningText.visibility = View.INVISIBLE
            val date = convert(timeText, dateText)
            val userId = shareViewModel.userId
            val task = Task(0, userId, title, description, categoryId, date, false)

            viewModel.addTask(task)
            findNavController().popBackStack()
        }
    }

    private fun convert(time: String, date: String): Date {
        val timeDate = SimpleDateFormat("EE, dd MMM yyyy HH:mm", Locale.getDefault())
        return timeDate.parse("$date $time")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}