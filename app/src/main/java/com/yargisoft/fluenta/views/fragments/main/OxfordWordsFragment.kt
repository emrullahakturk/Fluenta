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
import com.yargisoft.fluenta.viewmodel.OxfordWordViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OxfordWordsFragment : Fragment() {

    private val viewModel: OxfordWordViewModel  by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel.loadRandomQuote()
        // Sözleri gözlemle
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.word.collect { quote ->
                   Log.d("QuoteFragment", "Quote: $quote")
                }
            }
        }

        return inflater.inflate(R.layout.fragment_oxford_words, container, false)
    }

 }