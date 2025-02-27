package com.yargisoft.fluenta.views.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.yargisoft.fluenta.databinding.FragmentTranslatorBinding
import com.yargisoft.fluenta.viewmodel.TranslatorViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TranslatorFragment : Fragment() {

    private var _binding: FragmentTranslatorBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TranslatorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTranslatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnTranslate.setOnClickListener {
            val text = binding.etTextToTranslate.text.toString()
            viewModel.translate(text)
        }

        binding.btnSwapLanguages.setOnClickListener {
            val text = binding.etTextToTranslate.text.toString()
            viewModel.swapLanguages(text)
            swapUI()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.translatedText.collect { translatedText ->
                    binding.tvTranslatedText.text = translatedText
                }
            }
        }
    }

    private fun swapUI() {
        val tempText = binding.tvSourceLanguage.text
        binding.tvSourceLanguage.text = binding.tvTargetLanguage.text
        binding.tvTargetLanguage.text = tempText
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
