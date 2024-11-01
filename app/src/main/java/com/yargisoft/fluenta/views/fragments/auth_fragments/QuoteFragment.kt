package com.yargisoft.fluenta.views.fragments.auth_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.yargisoft.fluenta.R
import com.yargisoft.fluenta.databinding.FragmentQuoteBinding

class QuoteFragment : Fragment() {

    private lateinit var binding : FragmentQuoteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_quote, container, false)

        return binding.root
    }

}