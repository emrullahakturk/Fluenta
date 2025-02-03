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
import com.yargisoft.fluenta.R
import com.yargisoft.fluenta.databinding.FragmentOxfordWordsBinding
import com.yargisoft.fluenta.usecase.AddFavoriteUseCase
import com.yargisoft.fluenta.viewmodel.FavoriteViewModel
import com.yargisoft.fluenta.viewmodel.OxfordWordViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class OxfordWordsFragment : Fragment() {

    private val viewModel: OxfordWordViewModel by viewModels()
    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private lateinit var _binding: FragmentOxfordWordsBinding
    private val binding get() = _binding

    @Inject
    lateinit var addFavoriteUseCase: AddFavoriteUseCase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentOxfordWordsBinding.inflate(inflater, container, false)

        viewModel.loadRandomOxfordWord()

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
            binding.favoriteIcon.isClickable = false
            binding.btnSpeak.isClickable = false
            viewModel.ttsStop()
            if (!binding.diceLottie.isAnimating) {
                binding.diceLottie.playAnimation()
                CoroutineScope(Dispatchers.Main).launch {
                    viewModel.loadRandomOxfordWord()
                    repeat(10) {
                        delay(200)
                        viewModel.loadRandomOxfordWord()
                    }

                    if (favoriteViewModel.isWordFavorite(
                            binding.tvWord.text.toString(),
                            "oxford_word"
                        )
                    ) {
                        binding.favoriteIcon.setImageResource(R.drawable.ic_favorite_filled)
                    } else {
                        binding.favoriteIcon.setImageResource(R.drawable.ic_favorite)
                    }

                    binding.btnSpeak.isClickable = true
                    binding.favoriteIcon.isClickable = true
                }

            }
        }

        binding.btnSpeak.setOnClickListener {
            val word = "${binding.tvWord.text}  '.'   ${binding.tvEnExample.text} "
            viewModel.ttsSpeak(word, requireContext())
        }


        binding.favoriteIcon.setOnClickListener {
            addFavoriteUseCase.addFavorite(
                favoriteViewModel,
                "oxford_word",
                binding.tvWord.text.toString(),
                binding.tvType.text.toString(),
                binding.tvLevel.text.toString(),
                binding.tvMeaning.text.toString(),
                binding.tvTrExample.text.toString(),
                binding.tvEnExample.text.toString(),
                binding.favoriteIcon
            )
        }

        return binding.root
    }
}