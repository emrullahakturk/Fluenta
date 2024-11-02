package com.yargisoft.fluenta.views.fragments.auth_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.yargisoft.fluenta.R
import com.yargisoft.fluenta.databinding.FragmentPasswordBinding


class PasswordFragment : Fragment() {

    private lateinit var binding : FragmentPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_password, container, false)

        binding.forgotPasswordTv.setOnClickListener {
            findNavController().navigate(R.id.passwordToResetPassword)
        }

        binding.loginButton.setOnClickListener {
            findNavController().navigate(R.id.passwordToQuote)
        }

        return binding.root
    }


}