package com.example.todoapp.UI.MainApp.Trash.TaskDetailTrash

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapp.Model.Task
import com.example.todoapp.UI.MainApp.HomePage.TaskDetail.TaskDetailFragmentArgs
import com.example.todoapp.Utils.DatePicker.DateDialog
import com.example.todoapp.Utils.TimePicker.TimeDialog
import com.example.todoapp.databinding.FragmentTaskDetailBinding
import com.example.todoapp.databinding.FragmentTaskDetailTrashBinding
import java.util.Locale

class TaskDetailTrashFragment : Fragment() {
    private var _binding: FragmentTaskDetailTrashBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TaskDetailTrashViewModel by viewModels(){
        TaskDetailTrashViewModel.TaskDetailTrashViewModelFactory(requireContext())
    }
    private val args: TaskDetailFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskDetailTrashBinding.inflate(inflater, container, false)
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

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.restore.setOnClickListener {
            restore(id)
        }

        binding.delete.setOnClickListener {
            delete(id)
        }

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

    private fun delete(id: Long) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete Task")
        builder.setMessage("Task will be permanently deleted. You still want to delete them?")
        builder.setNegativeButton("OK") { dialog, which ->
            viewModel.delete(id)
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