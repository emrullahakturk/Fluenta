package com.yargisoft.fluenta.views.fragments.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.yargisoft.fluenta.databinding.FragmentStoryBinding
import com.yargisoft.fluenta.usecase.TextToSpeechUseCase
import com.yargisoft.fluenta.viewmodel.StoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class StoryFragment : Fragment() {

    @Inject
    lateinit var textToSpeechUseCase: TextToSpeechUseCase

    private var _binding: FragmentStoryBinding? = null
    private val binding get() = _binding!!
    private val storyViewModel: StoryViewModel by viewModels()

    private lateinit var sharedPreferences: SharedPreferences
    private var selectedFilter: String = "C1-C2" // Varsayılan seviye

    private var writingJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences =
            requireContext().getSharedPreferences("StoryPrefs", Context.MODE_PRIVATE)
        selectedFilter = sharedPreferences.getString("selected_filter", "C1-C2") ?: "C1-C2"

        setupFilterSelection()
        observeStoryChanges() // ViewModel’den gelen veriyi dinle
        loadSavedFilterStory()
        setupClickListeners()
    }

    private fun setupFilterSelection() {
        when (selectedFilter) {
            "A1-A2" -> binding.btnA1A2.isChecked = true
            "B1-B2" -> binding.btnB1B2.isChecked = true
            "C1-C2" -> binding.btnC1C2.isChecked = true
        }
    }

    private fun observeStoryChanges() {
        storyViewModel.story.observe(viewLifecycleOwner) { story ->
            animateText(story.title, story.story)
        }
    }

    private fun loadSavedFilterStory() {
        binding.btnGenerateStory.text = "Hikaye Üretiliyor..."
        binding.btnGenerateStory.isEnabled = false
        fetchStory()
    }

    private fun setupClickListeners() {

       /* binding.btnSpeak.setOnClickListener {
            val text = binding.tvStoryContent.text.toString()
            textToSpeechUseCase.speak(text)
        }*/

        binding.btnGenerateStory.setOnClickListener {
            generateStory()
        }

        binding.toggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                selectedFilter = when (checkedId) {
                    binding.btnA1A2.id -> "A1-A2"
                    binding.btnB1B2.id -> "B1-B2"
                    binding.btnC1C2.id -> "C1-C2"
                    else -> "C1-C2"
                }
                saveFilterSelection(selectedFilter)
                cancelCurrentStoryGeneration()
                fetchStory()
            }
        }

    }

    private fun fetchStory() {
        binding.btnGenerateStory.text = "Hikaye Üretiliyor..."
        binding.btnGenerateStory.isEnabled = false
        storyViewModel.fetchRandomStory(selectedFilter)
    }

    private fun generateStory() {
        cancelCurrentStoryGeneration()

        binding.btnGenerateStory.text = "Hikaye Üretiliyor..."
        binding.btnGenerateStory.isEnabled = false
        storyViewModel.fetchRandomStory(selectedFilter)
    }

    private fun animateText(title: String, text: String) {
        cancelCurrentStoryGeneration()

        binding.tvStoryTitle.text = title
        binding.tvStoryContent.text = ""

        writingJob = lifecycleScope.launch {
            val stringBuilder = StringBuilder()
            for (i in text.indices) {
                stringBuilder.append(text[i])
                binding.tvStoryContent.text = stringBuilder.toString()
                delay(15)
            }
            binding.btnGenerateStory.text = "Yeni Hikaye Üret"
            binding.btnGenerateStory.isEnabled = true
        }
    }

    private fun cancelCurrentStoryGeneration() {
        writingJob?.cancel()
        binding.tvStoryContent.text = ""
    }

    private fun saveFilterSelection(filter: String) {
        sharedPreferences.edit().putString("selected_filter", filter).apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
         textToSpeechUseCase.stop()
        _binding = null
    }

    override fun onStop() {
        super.onStop()
       textToSpeechUseCase.stop()
    }
}
