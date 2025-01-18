package com.yargisoft.fluenta.views.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.yargisoft.fluenta.databinding.FragmentMainPageBinding
import com.yargisoft.fluenta.viewmodel.MainPageViewModel
import com.yargisoft.fluenta.views.adapter.MainPageMenuAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainPageFragment @Inject constructor() : Fragment() {

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

        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.mainPageItems.collect { items ->
                adapter.updateData(items) { _ ->
                    // Tıklama olayları burada ele alınacak
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
