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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
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
        "main_to_about_us" to R.id.aboutUsFragment,
        "main_to_ai_tutor" to R.id.aiTutorFragment,
        "main_to_favorites" to R.id.favoritesFragment,
        "main_to_feedback" to R.id.feedbackFragment,
        "main_to_listen_and_learn" to R.id.listenAndLearnFragment,
        "main_to_most_common_phrases" to R.id.mostCommonPhrasesFragment,
        "main_to_most_common_words" to R.id.mostCommonWordsFragment,
        "main_to_my_account" to R.id.myAccountFragment,
        "main_to_oxford" to R.id.oxfordWordsFragment,
        "main_to_settings" to R.id.settingsFragment,
        "main_to_translator" to R.id.translatorFragment,
        "main_to_upgrade_pro" to R.id.upgradeProFragment
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

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
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
