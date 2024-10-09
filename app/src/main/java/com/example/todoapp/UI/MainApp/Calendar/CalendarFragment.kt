package com.example.todoapp.UI.MainApp.Calendar

import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.Adapter.RecyclerViewAdapter.CalendarAdapter
import com.example.todoapp.R
import com.example.todoapp.UI.SpacingItem
import com.example.todoapp.databinding.FragmentCalendarBinding
import java.text.SimpleDateFormat
import java.util.Date

class CalendarFragment : Fragment() {
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CalendarViewModel by viewModels {
        CalendarViewModel.CalendarViewModelFactory(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalendarBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.recyclerView
        val adapter = CalendarAdapter {
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        val space = resources.getDimensionPixelSize(R.dimen.space)
        recyclerView.addItemDecoration(SpacingItem(space))

        binding.calendarView.setOnDateChangeListener { _, year, month, day ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, day)

            val selectDate = calendar.time
            val dateFormat = SimpleDateFormat("EE, dd MMM yyyy")
            val date = dateFormat.format(selectDate)
            binding.date.setText(date)
            viewModel.listTask.observe(viewLifecycleOwner) { taskList ->
                val taskListFilter = taskList.filter { task ->
                    isSameDay(task.dueDate, selectDate)
                }
                if (taskListFilter.isNotEmpty()) {
                    adapter.setData(taskListFilter)
                } else adapter.setData(emptyList())
            }
        }
        viewModel.listCategory.observe(viewLifecycleOwner) { category ->
            adapter.setCategory(category)
        }
    }

    private fun isSameDay(dueDate: Date, selectDate: Date): Boolean {
        val calendar1 = Calendar.getInstance()
        val calendar2 = Calendar.getInstance()

        calendar1.time = dueDate
        calendar2.time = selectDate

        return (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) && calendar1.get(
            Calendar.MONTH
        ) == calendar2.get(Calendar.MONTH) && calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(
            Calendar.DAY_OF_MONTH
        ))

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}