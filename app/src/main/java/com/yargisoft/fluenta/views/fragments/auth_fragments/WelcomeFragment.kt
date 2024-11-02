package com.yargisoft.fluenta.views.fragments.auth_fragments

import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.yargisoft.fluenta.R
import com.yargisoft.fluenta.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {
    private lateinit var binding :FragmentWelcomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_welcome, container, false)

        //Giriş yap textini altı çizili konuma getirdik
        binding.loginTv.paintFlags =  Paint.UNDERLINE_TEXT_FLAG

        binding.signUpButton.setOnClickListener {
            findNavController().navigate(R.id.welcomeToSignUp)
        }

        binding.loginTv.setOnClickListener {
            findNavController().navigate(R.id.welcomeToLogin)
        }

        return binding.root
    }

}