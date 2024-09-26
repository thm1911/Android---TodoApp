package com.example.todoapp.UI.SignUp.User

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapp.Model.User
import com.example.todoapp.R
import com.example.todoapp.UI.ShareViewModel
import com.example.todoapp.databinding.FragmentUserSignUpBinding

class UserSignUpFragment : Fragment() {
    private var _binding: FragmentUserSignUpBinding? = null
    private val binding get() = _binding!!
    private val args: UserSignUpFragmentArgs by navArgs()
    private val viewModel: UserSignUpViewModel by viewModels {
        UserSignUpViewModel.UserSignUpViewModelFactory(requireContext())
    }
    private val shareViewModel: ShareViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserSignUpBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val email = args.email
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.userSignUpFragment, true)
            .setPopUpTo(R.id.emailSignUpFragment, true)
            .build()
        binding.signUp.setOnClickListener {
            check { check ->
                if (check) {
                    val user = User(
                        0,
                        email,
                        binding.username.text.toString(),
                        binding.password.text.toString(),
                        0
                    )
                    viewModel.insertUser(user){id ->
                        shareViewModel.userId = id
                    }
                    shareViewModel.userId = user.id
                    findNavController().navigate(
                        UserSignUpFragmentDirections.actionUserSignUpFragmentToHomepageChooseThemeFragment2(
                            binding.username.text.toString()
                        ),
                        navOptions
                    )
                }
            }
        }
    }

private fun check(callback: (Boolean) -> Unit) {
    val username = binding.username.text.toString()
    val password = binding.password.text.toString()
    val cfPassword = binding.cfPassword.text.toString()

    if (username.isEmpty()) {
        binding.layoutUsername.helperText = "Cannot be left blank"
        callback(false)
        return
    } else if (password.isEmpty()) {
        binding.layoutUsername.helperText = null
        binding.layoutPassword.helperText = "Cannot be left blank"
        callback(false)
        return
    } else if (cfPassword.isEmpty()) {
        binding.layoutPassword.helperText = null
        binding.LayoutCfPassword.helperText = "Cannot be left blank"
        callback(false)
        return
    } else if (!password.equals(cfPassword)) {
        binding.LayoutCfPassword.helperText = "Confirmation password is incorrect"
        callback(false)
        return
    }
    viewModel.isUserExist(username) { exist ->
        if (exist) {
            binding.LayoutCfPassword.helperText = null
            binding.layoutUsername.helperText = "Username already exists"
            callback(false)
        } else {
            binding.layoutUsername.helperText = null
            callback(true)
        }
    }
}

override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
}
}