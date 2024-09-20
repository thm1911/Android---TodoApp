package com.example.todoapp.UI.MainApp.ChooseTheme

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapp.R
import com.example.todoapp.UI.ShareViewModel
import com.example.todoapp.databinding.FragmentHomepageChooseThemeBinding

class HomepageChooseThemeFragment : Fragment() {
    private var _binding: FragmentHomepageChooseThemeBinding? = null
    private val binding get() = _binding!!
    private var listButton: List<RadioButton> = listOf()
    private val args: HomepageChooseThemeFragmentArgs by navArgs()
    private val viewModel: HomepageChooseThemeViewModel by viewModels {
        HomepageChooseThemeViewModel.HomepageChooseThemeViewModelFactory(requireContext())
    }
    private val shareViewModel: ShareViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomepageChooseThemeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainTheme = binding.mainTheme
        val blackTheme = binding.blackTheme
        val redTheme = binding.redTheme
        val blueTheme = binding.blueTheme
        var check = false
        val username = args.username

        listButton = listOf(mainTheme, blackTheme, redTheme, blueTheme)
        var theme = 0
        for (radioButton in listButton) {
            radioButton.setOnClickListener {
                theme = when (radioButton) {
                    mainTheme -> ContextCompat.getColor(requireContext(), R.color.main)
                    blackTheme -> ContextCompat.getColor(requireContext(), R.color.black)
                    redTheme -> ContextCompat.getColor(requireContext(), R.color.red)
                    blueTheme -> ContextCompat.getColor(requireContext(), R.color.blue)
                    else -> 0
                }
                check = true
                updateRadioButton(radioButton)
            }
        }

        val navOption =
            NavOptions.Builder().setPopUpTo(R.id.homepageChooseThemeFragment, true).build()

        binding.button.setOnClickListener {
            if (check) {
                viewModel.setTheme(username, theme)
                findNavController().navigate(
                    HomepageChooseThemeFragmentDirections.actionHomepageChooseThemeFragmentToMainAppFragment(), navOption
                )
            } else {
                binding.warning.visibility = View.VISIBLE
            }
        }
    }

    private fun updateRadioButton(radioButtonSelected: RadioButton) {
        for (radioButton in listButton) {
            radioButton.isChecked = radioButton == radioButtonSelected
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}