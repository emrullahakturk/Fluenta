package com.yargisoft.fluenta.views.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.yargisoft.fluenta.databinding.FragmentMostCommonWordsBinding
import com.yargisoft.fluenta.viewmodel.MostCommonWordViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MostCommonWordsFragment @Inject constructor() : Fragment() {


    private val viewModel: MostCommonWordViewModel by viewModels()
    private lateinit var _binding: FragmentMostCommonWordsBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMostCommonWordsBinding.inflate(inflater, container, false)
        viewModel.loadRandomMostCommonWord()

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
            viewModel.ttsStop()
            binding.btnSpeak.isClickable = false
            if (!binding.diceLottie.isAnimating) {
                binding.diceLottie.playAnimation()
                CoroutineScope(Dispatchers.Main).launch {
                    viewModel.loadRandomMostCommonWord()
                    repeat(10) {
                        delay(200)
                        viewModel.loadRandomMostCommonWord()
                    }
                    binding.btnSpeak.isClickable = true
                }

            }
        }

        binding.btnSpeak.setOnClickListener {
            val word = "${binding.tvWord.text}  '.'   ${binding.tvEnExample.text} "
            viewModel.ttsSpeak(word, requireContext())
            Toast.makeText(requireContext(), binding.tvWord.text.toString(), Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }


}