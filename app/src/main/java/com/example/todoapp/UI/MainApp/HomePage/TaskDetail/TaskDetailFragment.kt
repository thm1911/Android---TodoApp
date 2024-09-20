package com.example.todoapp.UI.MainApp.HomePage.TaskDetail

import android.app.DatePickerDialog
import android.icu.text.CaseMap.Title
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapp.Model.Task
import com.example.todoapp.UI.ShareViewModel
import com.example.todoapp.Utils.DatePicker.DateDialog
import com.example.todoapp.Utils.TimePicker.TimeDialog
import com.example.todoapp.databinding.FragmentTaskDetailBinding
import java.util.Locale

class TaskDetailFragment : Fragment() {
    private var _binding: FragmentTaskDetailBinding? = null
    private val binding get() = _binding!!
    private val args: TaskDetailFragmentArgs by navArgs()
    private val viewModel: TaskDetailViewModel by viewModels(){
        TaskDetailViewModel.TaskDetailViewModelFactory(requireContext())
    }
    private val shareViewModel: ShareViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = args.id
        var title = ""
        var description = ""
        var time = ""
        var date = ""
        var category = ""
        var isDelete = false

        viewModel.getTaskById(id).observe(viewLifecycleOwner){task ->
            title = task.title
            description = task.description
            category = task.category
            isDelete = task.isDelete
            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            val dateFormat = SimpleDateFormat("EE, dd MMM yyyy", Locale.getDefault())

            time = timeFormat.format(task.dueDate)
            date = dateFormat.format(task.dueDate)

            binding.title.setText(title)
            binding.description.setText(description)
            binding.time.setText(time)
            binding.date.setText(date)
            binding.category.setText(category)
        }

        binding.time.setOnClickListener {
            TimeDialog.selectTime(requireContext(), binding.time)
        }

        binding.date.setOnClickListener {
            DateDialog.selectDate(requireContext(), binding.date)
        }

        binding.update.setOnClickListener {
            update(id, title, description, time, date, category)
        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.delete.setOnClickListener {
            moveTotrash(id)
        }

        if(isDelete){
            binding.restore.visibility = View.VISIBLE

            binding.restore.setOnClickListener {
                restore(id)
            }

            binding.delete.setOnClickListener {
                delete(id)
            }
        }

    }

    private fun update(id: Long, title: String, description: String, time: String, date: String, category: String){
        val newTitle = binding.title.text.toString()
        val newDescription= binding.description.text.toString()
        val newTime = binding.time.text.toString()
        val newDate = binding.date.text.toString()
        val newCategory = binding.category.text.toString()

        if(title == newTitle && description == newDescription && time == newTime && date == newDate && category == newCategory){
            findNavController().popBackStack()
        }
        else{
            val date = SimpleDateFormat("EE, dd MMM yyyy HH:mm", Locale.getDefault())
            val task = Task(id,shareViewModel.userId, newTitle, newDescription, newCategory,false, date.parse("$newDate $newTime"))
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Update")
            builder.setMessage("Are you sure update Task?")
            builder.setNegativeButton("Ok"){dialog, which ->
                viewModel.updateTask(task)
                findNavController().popBackStack()
            }
            builder.setPositiveButton("Cancel"){dialog, which ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }
    }

    private fun moveTotrash(id: Long){

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete")
        builder.setMessage("The task will be placed in the trash. Do you still want to delete them?")
        builder.setNegativeButton("OK"){dialog, which ->
            viewModel.moveToTrash(id)
            findNavController().popBackStack()
        }

        builder.setPositiveButton("Cancel"){dialog, which ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun restore(id: Long){
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Restore Task")
        builder.setMessage("Are you sure you want to restore Task?")
        builder.setNegativeButton("OK"){dialog, which ->
            viewModel.restoreTask(id)
            findNavController().popBackStack()
        }
        builder.setPositiveButton("Cancel"){dialog, which ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun delete(id: Long){
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete Task")
        builder.setMessage("Task will be permanently deleted. You still want to delete them?")
        builder.setNegativeButton("OK"){dialog, which ->
            viewModel.delete(id)
            findNavController().popBackStack()
        }
        builder.setPositiveButton("Cancel"){dialog, which ->
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