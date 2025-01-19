package com.yargisoft.fluenta.views.fragments.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.yargisoft.fluenta.R
import com.yargisoft.fluenta.databinding.FragmentUsernameBinding

class UsernameFragment : Fragment() {

    private lateinit var binding : FragmentUsernameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentUsernameBinding.inflate(inflater, container, false)


        binding.goForwardButton.setOnClickListener {
            findNavController().navigate(R.id.usernameToCreatePassword)
        }




        return binding.root
    }


}