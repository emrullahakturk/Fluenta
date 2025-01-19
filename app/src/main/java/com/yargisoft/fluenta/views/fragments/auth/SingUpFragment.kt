package com.yargisoft.fluenta.views.fragments.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.yargisoft.fluenta.R
 import com.yargisoft.fluenta.databinding.FragmentSingUpBinding
import com.yargisoft.fluenta.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SingUpFragment @Inject constructor() : Fragment() {

    private var _binding: FragmentSingUpBinding? = null
    private val binding get() = _binding!!
    private val viewModel : AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding  = FragmentSingUpBinding.inflate(inflater,container,false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerButton.setOnClickListener {
            val fullName = binding.nameSurnameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            viewModel.register(fullName, email, password)
        }
        binding.arrowBackImageView.setOnClickListener{
            findNavController().popBackStack()
        }
        binding.loginTv.setOnClickListener {
            findNavController().navigate(R.id.signUpToLogin)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.registerButton.isEnabled = !isLoading
        })

        viewModel.registerResult.observe(viewLifecycleOwner, Observer { result ->
            result.onSuccess {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                // Başarılı işlem sonrası bir sonraki ekrana yönlendirme
                findNavController().navigate(R.id.signUpToLogin)
            }
            result.onFailure { error ->
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}