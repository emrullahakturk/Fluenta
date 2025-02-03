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

        binding.apply {
            item.apply {

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

                diceLottie.setOnClickListener {
                    favoriteIcon.isClickable = false
                    btnSpeak.isClickable = false
                    viewModel.ttsStop()
                    if (!diceLottie.isAnimating) {
                        diceLottie.playAnimation()
                        CoroutineScope(Dispatchers.Main).launch {
                            viewModel.loadRandomOxfordWord()
                            repeat(10) {
                                delay(200)
                                viewModel.loadRandomOxfordWord()
                            }

                            if (favoriteViewModel.isWordFavorite(
                                    tvWord.text.toString(),
                                    "oxford_word"
                                )
                            ) {
                                favoriteIcon.setImageResource(R.drawable.ic_favorite_filled)
                            } else {
                                favoriteIcon.setImageResource(R.drawable.ic_favorite)
                            }

                            btnSpeak.isClickable = true
                            favoriteIcon.isClickable = true
                        }

                    }
                }


                btnSpeak.setOnClickListener {
                    val word = "${tvWord.text}  '.'   ${tvEnExample.text} "
                    viewModel.ttsSpeak(word, requireContext())
                }


                favoriteIcon.setOnClickListener {
                    addFavoriteUseCase.addFavorite(
                        favoriteViewModel,
                        "oxford_word",
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

            return root
        }
    }
}