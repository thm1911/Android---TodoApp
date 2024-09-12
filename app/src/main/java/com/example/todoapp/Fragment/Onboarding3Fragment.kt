package com.example.todoapp.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.todoapp.Fragment.StepFragmentDirections
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentOnboarding3Binding

class Onboarding3Fragment : Fragment() {
    private var _binding: FragmentOnboarding3Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnboarding3Binding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}