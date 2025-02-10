package com.yargisoft.fluenta.views.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.yargisoft.fluenta.R
import com.yargisoft.fluenta.databinding.FragmentFavoritesBinding


class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        binding.apply {
            btnOxford.root.setOnClickListener {
                findNavController().navigate(R.id.favorites_to_oxford_word)
            }
            btnOxford.ivIcon.setImageResource(R.drawable.favorite_oxford_words)

            btnC1C2.root.setOnClickListener {
                findNavController().navigate(R.id.favorites_to_common_word)
            }
            btnC1C2.ivIcon.setImageResource(R.drawable.favorite_c1_c2_words)

            btnPhrases.root.setOnClickListener {
                findNavController().navigate(R.id.favorite_to_phrases)
            }
            btnPhrases.ivIcon.setImageResource(R.drawable.favorite_common_phrases)
        }

        return binding.root
    }
}