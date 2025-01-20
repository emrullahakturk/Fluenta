package com.yargisoft.fluenta.views.fragments.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.yargisoft.fluenta.R
import com.yargisoft.fluenta.databinding.FragmentOxfordWordsBinding
import com.yargisoft.fluenta.viewmodel.OxfordWordViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.text.Typography.quote

@AndroidEntryPoint
class OxfordWordsFragment : Fragment() {

    private val viewModel: OxfordWordViewModel  by viewModels()
    private lateinit var _binding: FragmentOxfordWordsBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        _binding = FragmentOxfordWordsBinding.inflate(inflater, container, false)

        viewModel.loadRandomQuote()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.word.collect { word ->
                    binding.apply {
                        tvWord.text = word?.word
                        tvType.text = word?.type
                        tvLevel.text = word?.level
                        tvMeaning.text = word?.meaning
                        tvEnExample.text = word?.enExample
                        tvTrExample.text = word?.trExample
                    }
                }
            }
        }



        binding.diceLottie.setOnClickListener {
            if (!binding.diceLottie.isAnimating) {
                binding.diceLottie.playAnimation()
                CoroutineScope(Dispatchers.Main).launch {
                    viewModel.loadRandomQuote()
                    delay(200)
                    viewModel.loadRandomQuote()
                    delay(200)
                    viewModel.loadRandomQuote()
                    delay(200)
                    viewModel.loadRandomQuote()
                    delay(200)
                    viewModel.loadRandomQuote()
                    delay(200)
                    viewModel.loadRandomQuote()
                    delay(200)
                    viewModel.loadRandomQuote()
                    delay(200)
                    viewModel.loadRandomQuote()
                    delay(200)
                    viewModel.loadRandomQuote()
                    delay(200)
                    viewModel.loadRandomQuote()
                    delay(200)

                }

            }
        }

        return binding.root
    }

 }