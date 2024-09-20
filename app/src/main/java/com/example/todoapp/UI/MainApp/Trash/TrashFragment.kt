package com.example.todoapp.UI.MainApp.Trash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.Adapter.RecyclerViewAdapter.TaskAdapter
import com.example.todoapp.R
import com.example.todoapp.UI.ShareViewModel
import com.example.todoapp.UI.SpacingItem
import com.example.todoapp.databinding.FragmentTrashBinding

class TrashFragment : Fragment() {
    private var _binding: FragmentTrashBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TrashViewModel by viewModels{
        TrashViewModel.TrashViewModelFactory(shareViewModel, requireContext())
    }
    private val shareViewModel: ShareViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTrashBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.recyclerView
        val adapter = TaskAdapter{ task ->
            val id = task.id
            findNavController().navigate(TrashFragmentDirections.actionTrashFragmentToTaskDetailTrashFragment(id))
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        viewModel.listDeleteTask.observe(viewLifecycleOwner){task ->
            adapter.setData(task)
        }

        val space = resources.getDimensionPixelSize(R.dimen.space)
        recyclerView.addItemDecoration(SpacingItem(space))
        viewModel.getTheme(shareViewModel.userId){theme ->
            adapter.setTheme(theme)
        }

        binding.restore.setOnClickListener {
            restoreAll()
        }
    }

    private fun restoreAll(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Restore All task")
        builder.setMessage("Are you sure you want to restore them all?")
        builder.setPositiveButton("OK"){dialog, which ->
            viewModel.getAllRestore()
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel"){dialog, which ->
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