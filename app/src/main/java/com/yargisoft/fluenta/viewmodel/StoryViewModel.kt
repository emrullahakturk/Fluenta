package com.yargisoft.fluenta.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yargisoft.fluenta.data.model.Story
import com.yargisoft.fluenta.data.repository.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor(private val repository: StoryRepository) : ViewModel() {

    private val _story = MutableLiveData<Story>()
    val story: LiveData<Story> get() = _story

    fun fetchRandomStory(level: String) {
        viewModelScope.launch {
            val randomStory = repository.getRandomStoryByLevel(level)
            _story.postValue(randomStory)
        }
    }
}
