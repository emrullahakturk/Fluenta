package com.yargisoft.fluenta.views.fragments.auth_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.yargisoft.fluenta.R
import com.yargisoft.fluenta.databinding.FragmentEnterPasswordBinding


class EnterPasswordFragment : Fragment() {

    private lateinit var binding : FragmentEnterPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        binding = FragmentEnterPasswordBinding.inflate(inflater, container, false)
        binding.arrowBackImageView.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.forgotPasswordTv.setOnClickListener {
            findNavController().navigate(R.id.passwordToResetPassword)
        }

        binding.loginButton.setOnClickListener {
            findNavController().navigate(R.id.passwordToQuote)
        }

        return binding.root
    }


}