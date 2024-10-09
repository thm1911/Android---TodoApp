package com.example.todoapp.UI.MainApp.HomePage.Task.TaskDetail

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapp.Model.Task
import com.example.todoapp.Utils.DatePicker.DateDialog
import com.example.todoapp.Utils.Notification
import com.example.todoapp.Utils.SharePref
import com.example.todoapp.Utils.TimePicker.TimeDialog
import com.example.todoapp.databinding.FragmentTaskDetailBinding
import java.util.Locale

class TaskDetailFragment : Fragment() {
    private var _binding: FragmentTaskDetailBinding? = null
    private val binding get() = _binding!!
    private val args: TaskDetailFragmentArgs by navArgs()
    private val viewModel: TaskDetailViewModel by viewModels() {
        TaskDetailViewModel.TaskDetailViewModelFactory(requireActivity().application)
    }

    private var title = ""
    private var description = ""
    private var time = ""
    private var date = ""
    private var categoryId = 0L
    private var nameCategory = ""
    private var isDelete = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mes = args.mes
        val id = args.id
        nameCategory = binding.category.text.toString()

        if (mes == "categoryId") {
            init(SharePref.getTaskIdFromPreferences(requireActivity().application), id)
        } else if (mes == "taskId")  {
            SharePref.setTaskIdToPreferences(requireActivity().application, id)
            init(id, 0L)
        }

        binding.time.setOnClickListener {
            TimeDialog.selectTime(requireContext(), binding.time)
        }

        binding.date.setOnClickListener {
            DateDialog.selectDate(requireContext(), binding.date)
        }

        binding.category.setOnClickListener {
            updateCategory()
        }

        binding.update.setOnClickListener {
            update(SharePref.getTaskIdFromPreferences(requireActivity().application))
        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.done.setOnClickListener {
            viewModel.doneTask(id)
            findNavController().popBackStack()
        }


        binding.delete.setOnClickListener {
            moveTotrash(id)
        }

    }

    private fun init(taskId: Long, newCategoryId: Long) {
        if (taskId != 0L) {
            viewModel.getTaskById(taskId).observe(viewLifecycleOwner) { task ->
                title = task.title
                description = task.description
                isDelete = task.isDelete
                if (newCategoryId == 0L) categoryId = task.categoryId
                else categoryId = newCategoryId
                val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                val dateFormat = SimpleDateFormat("EE, dd MMM yyyy", Locale.getDefault())

                time = timeFormat.format(task.dueDate)
                date = dateFormat.format(task.dueDate)

                binding.title.setText(title)
                binding.description.setText(description)
                binding.time.setText(time)
                binding.date.setText(date)

                viewModel.getCategoryById(categoryId).observe(viewLifecycleOwner) { category ->
                    val name = category.name
                    binding.category.setText(name)
                }
            }
        }
    }

    private fun updateCategory() {
        findNavController().navigate(
            TaskDetailFragmentDirections.actionTaskDetailFragmentToCategoryFragment(
                "Task Detail"
            )
        )
    }

    private fun update(taskId: Long) {
        val newTitle = binding.title.text.toString()
        val newDescription = binding.description.text.toString()
        val newTime = binding.time.text.toString()
        val newDate = binding.date.text.toString()
        val newNameCategory = binding.category.text.toString()

        if (title == newTitle && description == newDescription && time == newTime && date == newDate && nameCategory == newNameCategory) {
            findNavController().popBackStack()
        } else if (newTitle.isEmpty() || newDescription.isEmpty() || newTime.isEmpty() || newDate.isEmpty() || newNameCategory.isEmpty()) {
            binding.warningText.visibility = View.VISIBLE
        } else {
            val date = SimpleDateFormat("EE, dd MMM yyyy HH:mm", Locale.getDefault())
            val task = Task(
                taskId,
                SharePref.getUserIdFromPreferences(requireActivity().application),
                newTitle,
                newDescription,
                categoryId,
                date.parse("$newDate $newTime"),
                false,
                false
            )
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Update")
            builder.setMessage("Are you sure update Task?")
            builder.setNegativeButton("Ok") { dialog, which ->
                viewModel.updateTask(task)
                Notification.notificationTask(requireContext(), task)
                findNavController().popBackStack()
            }
            builder.setPositiveButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }
    }

    private fun moveTotrash(id: Long) {

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete")
        builder.setMessage("The task will be placed in the trash. Do you still want to delete them?")
        builder.setNegativeButton("OK") { dialog, which ->
            viewModel.moveToTrash(id)
            findNavController().popBackStack()
        }

        builder.setPositiveButton("Cancel") { dialog, which ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}