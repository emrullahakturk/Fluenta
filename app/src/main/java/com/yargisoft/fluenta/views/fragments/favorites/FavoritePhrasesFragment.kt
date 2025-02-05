package com.yargisoft.fluenta.views.fragments.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.yargisoft.fluenta.databinding.FragmentFavoritePhrasesBinding
import com.yargisoft.fluenta.viewmodel.FavoriteViewModel
import com.yargisoft.fluenta.views.adapter.PhrasesFavoritesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FavoritePhrasesFragment : Fragment() {

    private var _binding: FragmentFavoritePhrasesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoriteViewModel by viewModels()

    @Inject
    lateinit var adapter: PhrasesFavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritePhrasesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadFavorites("common_phrases")
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favorites.collect { favorites ->
                    adapter.updateList(favorites)
                }
            }
        }

        adapter.setOnClickBtnRemoveListener { phrase ->
            viewModel.removeFavorite(phrase, "common_phrases")
            viewModel.loadFavorites("common_phrases")
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.ttsStop()
        _binding = null
    }

    override fun onStop() {
        super.onStop()
        adapter.ttsStop()
    }

}
