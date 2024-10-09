package com.example.todoapp.UI.MainApp.Settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentSettingsBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingsViewModel by viewModels() {
        SettingsViewModel.SettingsViewModelFactory(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottom_nav = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottom_nav.visibility = View.VISIBLE

        initUsername()

        binding.account.setOnClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToAccountFragment())
            bottom_nav.visibility = View.GONE
        }

        binding.theme.setOnClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToThemeFragment())
            bottom_nav.visibility = View.GONE
        }

        binding.information.setOnClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToInformationFragment())
            bottom_nav.visibility = View.GONE
        }

        binding.logout.setOnClickListener {
            val navHostFragment =
                requireActivity().supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            val navController = navHostFragment.navController
            navController.navigate(R.id.createAccountFragment)

            navController.popBackStack(R.id.mainAppFragment, true)
            findNavController().popBackStack()
        }

    }

    private fun initUsername() {
        viewModel.listUser.observe(viewLifecycleOwner) { user ->
            val username = user.username
            binding.username.setText(username)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}