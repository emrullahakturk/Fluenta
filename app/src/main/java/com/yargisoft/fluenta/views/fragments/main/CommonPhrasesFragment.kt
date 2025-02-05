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
import com.yargisoft.fluenta.databinding.FragmentCommonPhrasesBinding
import com.yargisoft.fluenta.usecase.AddFavoriteUseCase
import com.yargisoft.fluenta.viewmodel.CommonPhraseViewModel
import com.yargisoft.fluenta.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CommonPhrasesFragment @Inject constructor() : Fragment() {

    private val viewModel: CommonPhraseViewModel by viewModels()
    private lateinit var _binding: FragmentCommonPhrasesBinding
    private val binding get() = _binding
    private val favoriteViewModel: FavoriteViewModel by viewModels()

    @Inject
    lateinit var addFavoriteUseCase: AddFavoriteUseCase


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCommonPhrasesBinding.inflate(inflater, container, false)

        viewModel.loadRandomMostCommonWord()

       binding.apply {
           item.apply {
               viewLifecycleOwner.lifecycleScope.launch {
                   viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                       viewModel.phrase.collect { phrase ->
                           apply {
                               tvPhrase.text = phrase?.phrase
                               tvLevel.text = phrase?.level
                               tvMeaning.text = phrase?.meaning
                               tvEnExample.text = phrase?.enExample
                               tvTrExample.text = phrase?.trExample
                           }
                       }
                   }
               }

               diceLottie.setOnClickListener {
                   btnSpeak.isClickable = false
                   viewModel.ttsStop()
                   if (!diceLottie.isAnimating) {
                       diceLottie.playAnimation()
                       CoroutineScope(Dispatchers.Main).launch {
                           viewModel.loadRandomMostCommonWord()
                           repeat(10) {
                               delay(200)
                               viewModel.loadRandomMostCommonWord()
                           }
                           btnSpeak.isClickable = true
                       }

                   }
               }

               btnSpeak.setOnClickListener {
                   val phrase = "${tvPhrase.text}  '.'   ${tvEnExample.text} "
                   viewModel.ttsSpeak(phrase, requireContext())
                   Toast.makeText(requireContext(), tvPhrase.text.toString(), Toast.LENGTH_SHORT)
                       .show()
               }

               favoriteIcon.setOnClickListener {
                   addFavoriteUseCase.addFavorite(
                       favoriteViewModel,
                       "common_phrases",
                       tvPhrase.text.toString(),
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