package com.yargisoft.fluenta.views.fragments.auth_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
 import androidx.navigation.fragment.findNavController
import com.yargisoft.fluenta.R
 import com.yargisoft.fluenta.databinding.FragmentSingUpBinding


class SingUpFragment : Fragment() {

    private lateinit var binding : FragmentSingUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSingUpBinding.inflate(inflater,container,false)

        binding.registerButton.setOnClickListener {
            findNavController().navigate(R.id.signUpToUsername)
        }

        binding.loginTv.setOnClickListener {
            findNavController().navigate(R.id.signUpToLogin)
        }

        return binding.root
    }
}