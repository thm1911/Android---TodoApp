package com.example.todoapp.UI.Onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.todoapp.Adapter.ViewPagerAdapter.FragmentOnboardingAdapter
import com.example.todoapp.databinding.FragmentStepBinding
import com.google.android.material.tabs.TabLayoutMediator

class StepFragment : Fragment() {
    private var _binding: FragmentStepBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStepBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout
        val adapter = FragmentOnboardingAdapter(parentFragmentManager, lifecycle)
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager){tab, position ->

        }.attach()

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                if(position == 0){
                    binding.skip.visibility = View.VISIBLE
                    binding.continueButton.visibility = View.GONE
                }
                if(position == 1){
                    binding.skip.visibility = View.VISIBLE
                    binding.continueButton.visibility = View.GONE
                }
                if (position == 2){
                    binding.skip.visibility = View.GONE
                    binding.continueButton.visibility = View.VISIBLE
                }
            }
        })

        binding.skip.setOnClickListener {
            findNavController().navigate(StepFragmentDirections.actionStepFragmentToCreateAccountFragment())
        }

        binding.continueButton.setOnClickListener {
            findNavController().navigate(StepFragmentDirections.actionStepFragmentToCreateAccountFragment())
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}