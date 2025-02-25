package com.yargisoft.fluenta.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yargisoft.fluenta.data.model.OxfordWord
import com.yargisoft.fluenta.data.repository.OxfordWordRepository
import com.yargisoft.fluenta.usecase.TextToSpeechUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OxfordWordViewModel @Inject constructor(
    private val repository: OxfordWordRepository,
    private val textToSpeechUseCase: TextToSpeechUseCase
) : ViewModel() {

    private val _word = MutableStateFlow<OxfordWord?>(null)
    val word: StateFlow<OxfordWord?> get() = _word

    fun loadRandomOxfordWord() {
        viewModelScope.launch {
            try {
                _word.value = repository.getRandomOxfordWord()
            } catch (e: Exception) {
                // Hata yönetimi eklenebilir
                _word.value = null
            }
        }
    }

    fun ttsSpeak(text: String, context: Context) {
        if (textToSpeechUseCase.isTextToSpeechEnabled(context)){
            textToSpeechUseCase.speak(text)
        }else{
            textToSpeechUseCase.alertBuilderForTextToSpeech(context)
        }
    }

    fun ttsStop() {
        textToSpeechUseCase.stop()
    }
}