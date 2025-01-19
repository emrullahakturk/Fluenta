package com.yargisoft.fluenta.views.fragments.auth

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.yargisoft.fluenta.R
import com.yargisoft.fluenta.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {
    private lateinit var binding: FragmentWelcomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentWelcomeBinding.inflate(inflater, container, false)

        //Giriş yap textini altı çizili konuma getirdik
        binding.loginTv.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        binding.signUpButton.setOnClickListener {
            findNavController().navigate(R.id.welcomeToSignUp)
        }

        binding.loginTv.setOnClickListener {
            findNavController().navigate(R.id.welcomeToLogin)
        }

        return binding.root
    }

}