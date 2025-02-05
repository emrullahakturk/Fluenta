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
import com.yargisoft.fluenta.usecase.AddFavoriteUseCase
import com.yargisoft.fluenta.viewmodel.FavoriteViewModel
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
    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private lateinit var _binding: FragmentMostCommonWordsBinding
    private val binding get() = _binding

    @Inject
    lateinit var addFavoriteUseCase: AddFavoriteUseCase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMostCommonWordsBinding.inflate(inflater, container, false)
        viewModel.loadRandomMostCommonWord()


        binding.apply {

            item.apply {

                binding.diceLottie.setOnClickListener {
                    viewModel.ttsStop()
                    btnSpeak.isClickable = false
                    if (!binding.diceLottie.isAnimating) {
                        binding.diceLottie.playAnimation()
                        CoroutineScope(Dispatchers.Main).launch {
                            viewModel.loadRandomMostCommonWord()
                            repeat(10) {
                                delay(200)
                                viewModel.loadRandomMostCommonWord()
                            }
                            binding.item.btnSpeak.isClickable = true
                        }

                    }
                }

                viewLifecycleOwner.lifecycleScope.launch {
                    viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        viewModel.word.collect { word ->

                            tvWord.text = word?.word
                            tvType.text = word?.type
                            tvLevel.text = word?.level
                            tvMeaning.text = word?.meaning
                            tvEnExample.text = word?.enExample
                            tvTrExample.text = word?.trExample

                        }
                    }
                }

                btnSpeak.setOnClickListener {
                    val word = "${tvWord.text}  '.'   ${tvEnExample.text} "
                    viewModel.ttsSpeak(word, requireContext())
                    Toast.makeText(requireContext(), tvWord.text.toString(), Toast.LENGTH_SHORT)
                        .show()
                }

                favoriteIcon.setOnClickListener {
                    addFavoriteUseCase.addFavorite(
                        favoriteViewModel,
                        "common_words",
                        tvWord.text.toString(),
                        tvType.text.toString(),
                        tvLevel.text.toString(),
                        tvMeaning.text.toString(),
                        tvTrExample.text.toString(),
                        tvEnExample.text.toString(),
                        favoriteIcon
                    )
                }
            }

        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.ttsStop()
    }

    override fun onStop() {
        super.onStop()
        viewModel.ttsStop()
    }

}