package com.example.todoapp.UI.MainApp.Settings.Account.ChangePassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.UI.ShareViewModel
import com.example.todoapp.databinding.FragmentChangePasswordBinding

class ChangePasswordFragment : Fragment() {
    private var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!
    private val shareViewModel: ShareViewModel by activityViewModels()
    private val viewModel: ChangePasswordViewModel by viewModels() {
        ChangePasswordViewModel.ChangePasswordViewModelFactory(shareViewModel, requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChangePasswordBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var pass = ""
        viewModel.listUser.observe(viewLifecycleOwner) { user ->
            pass = user.password
        }

        binding.save.setOnClickListener {
            checkPass(pass)
        }

        binding.cancel.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun checkPass(pass: String) {
        val inputPass = binding.pass.text.toString()
        val newPass = binding.newPass.text.toString()
        val cfNewPass = binding.cfNewPass.text.toString()

        if (inputPass != pass) {
            binding.layoutPassword.helperText = "Password is incorrect"
        } else if (newPass.isNullOrEmpty()) {
            binding.layoutPassword.helperText = ""
            binding.layoutNewPassword.helperText = "New password cannot be blank"
        } else if (newPass != cfNewPass) {
            binding.layoutNewPassword.helperText = ""
            binding.layoutPassword.helperText = ""
            binding.layoutCfNewPassword.helperText = "Password is incorrect"
        } else {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Change Password")
            builder.setMessage("Are you sure change password?")
            builder.setPositiveButton("OK") { dialog, which ->
                viewModel.updatePassword(shareViewModel.userId, newPass)
                findNavController().popBackStack()
            }

            builder.setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}