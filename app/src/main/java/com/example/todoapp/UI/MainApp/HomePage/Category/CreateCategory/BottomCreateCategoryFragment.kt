package com.example.todoapp.UI.MainApp.HomePage.Category.CreateCategory

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.todoapp.Model.Category
import com.example.todoapp.R
import com.example.todoapp.UI.ShareViewModel
import com.example.todoapp.databinding.FragmentBottomCreateCategoryBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.coroutines.cancellation.CancellationException

class BottomCreateCategoryFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentBottomCreateCategoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BottomCreateCategoryViewModel by viewModels {
        BottomCreateCategoryViewModel.BottomCreateCategoryViewModelFactory(requireContext())
    }
    private val shareViewModel: ShareViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomCreateCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.close.setOnClickListener {
            dismiss()
        }

        binding.color.setOnClickListener {
            selectColor()
        }

        binding.done.setOnClickListener {
            check()
        }
    }

    private fun selectColor(){
        val listColor = resources.getStringArray(R.array.color_list)

        val colorDialogView = layoutInflater.inflate(R.layout.dialog_color_picker,null)
        val colorDialog = AlertDialog.Builder(requireContext())
            .setTitle("Color")
            .setView(colorDialogView)
            .create()

        val gridLayout = colorDialogView.findViewById<GridLayout>(R.id.gridColor)

        listColor.forEach { color ->
            val colorButton = View(requireContext()).apply {
                layoutParams = GridLayout.LayoutParams().apply {
                    width = 170
                    height = 170
                    setMargins(20, 20, 20, 20)
                }
                setBackgroundColor(Color.parseColor(color))
                setOnClickListener {
                    binding.color.setBackgroundColor(Color.parseColor(color))
                    binding.hex.setText(color)
                    colorDialog.cancel()
                }
            }

            gridLayout.addView(colorButton)

        }
        colorDialog.show()

    }

    private fun check(){
        val name = binding.name.text.toString()
        val hexColor = binding.hex.text.toString()

        if(name.isEmpty() || hexColor == "Select color"){
            binding.warningText.visibility = View.VISIBLE
        }
        else{
            binding.warningText.visibility = View.INVISIBLE
            insertCategory()
            dismiss()
        }
    }

    private fun insertCategory(){
        val name = binding.name.text.toString()
        val hexColor = binding.hex.text.toString()
        val userId = shareViewModel.userId

        val category = Category(0, userId, name, hexColor)
        viewModel.insertCategory(category)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}