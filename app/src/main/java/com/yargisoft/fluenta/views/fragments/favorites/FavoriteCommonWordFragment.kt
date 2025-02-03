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
import com.yargisoft.fluenta.databinding.FragmentFavoriteCommonWordBinding
import com.yargisoft.fluenta.viewmodel.FavoriteViewModel
import com.yargisoft.fluenta.views.adapter.CommonWordsFavoritesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteCommonWordFragment : Fragment() {

    private var _binding: FragmentFavoriteCommonWordBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoriteViewModel by viewModels()

    @Inject
    lateinit var adapter: CommonWordsFavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteCommonWordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadFavorites("common_words")

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favorites.collect { favorites ->
                    adapter.updateList(favorites)
                }
            }
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter.setOnClickBtnRemoveListener { word ->
            viewModel.removeFavorite(word, "common_words")
            viewModel.loadFavorites("common_words")
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
