package com.example.todoapp.UI.SignIn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.todoapp.R
import com.example.todoapp.UI.ShareViewModel
import com.example.todoapp.databinding.FragmentUserSignInBinding

class UserSignInFragment : Fragment() {
    private var _binding: FragmentUserSignInBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserSignInViewModel by viewModels {
        UserSignInViewModel.UserSignInViewModelFactory(requireContext())
    }
    private val shareViewModel: ShareViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserSignInBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.userSignInFragment, true)
            .build()
        binding.signIn.setOnClickListener {
            check { res ->
                if (res){
                    viewModel.getId(binding.username.text.toString()){id ->
                        shareViewModel.userId = id
                    }
                    findNavController().navigate(
                        UserSignInFragmentDirections.actionUserSignInFragmentToHomepageChooseThemeFragment(binding.username.text.toString()),
                        navOptions)
                }
            }
        }
    }

    private fun check(callback: (Boolean) -> Unit) {
        val username = binding.username.text.toString()
        val pass = binding.password.text.toString()
        if (username.isEmpty()) {
            binding.layoutUsername.helperText = "Cannot be left blank"
            callback(false)
            return
        }
        viewModel.checkUser(username, pass) { check ->
            if (check) {
                callback(true)
            } else {
                binding.layoutUsername.helperText = null
                binding.layoutPassword.helperText = "Password is incorrect"
                callback(false)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}