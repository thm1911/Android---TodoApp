package com.example.todoapp.UI.MainApp.HomePage.CreateTask

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.text.Layout.Directions
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todoapp.Model.Task
import com.example.todoapp.R
import com.example.todoapp.UI.ShareViewModel
import com.example.todoapp.Utils.DatePicker.DateDialog
import com.example.todoapp.Utils.TimePicker.TimeDialog
import com.example.todoapp.databinding.FragmentBottomCreateTaskBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BottomCreateTaskFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentBottomCreateTaskBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BottomCreateTaskViewModel by viewModels(){
        BottomCreateTaskViewModel.BottomCreateTaskViewModelFactory(requireContext())
    }
    private val shareViewModel: ShareViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomCreateTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.close.setOnClickListener {
            dismiss()
        }

        binding.time.setOnClickListener {
            TimeDialog.selectTime(requireContext(), binding.timeText)
        }

        binding.date.setOnClickListener {
            DateDialog.selectDate(requireContext(), binding.dateText)
        }

        binding.category.setOnClickListener {
            selectCategory()
        }

        binding.enter.setOnClickListener {
            insertTask()
        }
    }



    private fun selectCategory(){
        findNavController().navigate(R.id.action_homePageFragment_to_categoryFragment)
    }

    private fun insertTask(){
        val title = binding.title.text.toString()
        val description = binding.description.text.toString()
        val timeText = binding.timeText.text.toString()
        val dateText = binding.dateText.text.toString()

        if(title.isEmpty() || description.isEmpty() || timeText.isEmpty() || dateText.isEmpty()){
            binding.warningText.visibility = View.VISIBLE
        }
        else{
            binding.warningText.visibility = View.INVISIBLE
            val date = convert(timeText, dateText)
            val userId = shareViewModel.userId
            val task = Task(0, userId, title, description, "hihi",false, date )

            viewModel.addTask(task)
            dismiss()
        }
    }

    private fun convert(time: String, date: String): Date{
        val timeDate = SimpleDateFormat("EE, dd MMM yyyy HH:mm", Locale.getDefault())
        return timeDate.parse("$date $time")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}