package com.yargisoft.fluenta.views.fragments.auth_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.yargisoft.fluenta.R
import com.yargisoft.fluenta.databinding.FragmentCreatePasswordBinding


class CreatePasswordFragment : Fragment() {

    private lateinit var binding : FragmentCreatePasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCreatePasswordBinding.inflate(inflater, container, false)

        binding.registerButton.setOnClickListener {
            findNavController().navigate(R.id.createPasswordToQuote)
        }

        return binding.root
    }

}