package com.example.todoapp.UI.MainApp.Settings.Account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todoapp.UI.ShareViewModel
import com.example.todoapp.databinding.FragmentAccountBinding

class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private val shareViewModel: ShareViewModel by activityViewModels()
    private val viewModel: AccountViewModel by viewModels {
        AccountViewModel.AccountViewModelFactory(shareViewModel, requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        binding.changePass.setOnClickListener {
            findNavController().navigate(AccountFragmentDirections.actionAccountFragmentToChangePasswordFragment())
        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun init() {
        viewModel.listUser.observe(viewLifecycleOwner) { user ->
            binding.username.setText(user.username)
            binding.email.setText(user.email)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}