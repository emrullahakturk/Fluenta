package com.yargisoft.fluenta.views.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.yargisoft.fluenta.R
import com.yargisoft.fluenta.databinding.FragmentMainPageBinding
import com.yargisoft.fluenta.viewmodel.MainPageViewModel
import com.yargisoft.fluenta.views.adapter.MainPageMenuAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainPageFragment @Inject constructor() : Fragment() {


    private val navigationActions: Map<String, Int> = mapOf(
        "main_to_about_us" to R.id.main_to_about_us,
        "main_to_ai_tutor" to R.id.main_to_ai_tutor,
        "main_to_favorites" to R.id.main_to_favorites,
        "main_to_feedback" to R.id.main_to_feedback,
        "main_to_listen_and_learn" to R.id.main_to_listen_and_learn,
        "main_to_most_common_phrases" to R.id.main_to_most_common_phrases,
        "main_to_most_common_words" to R.id.main_to_most_common_words,
        "main_to_my_account" to R.id.main_to_my_account,
        "main_to_oxford" to R.id.main_to_oxford,
        "main_to_settings" to R.id.main_to_settings,
        "main_to_translator" to R.id.main_to_translator,
        "main_to_upgrade_pro" to R.id.main_to_upgrade_pro
    )


    @Inject
    lateinit var adapter: MainPageMenuAdapter

    private var _binding: FragmentMainPageBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainPageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.mainPageItems.collect { items ->
                adapter.updateData(items) { item ->
                    navigationActions[item.destination]?.let { findNavController().navigate(it) }
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
