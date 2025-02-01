package com.yargisoft.fluenta.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yargisoft.fluenta.data.model.CommonPhrase
import com.yargisoft.fluenta.data.repository.CommonPhraseRepository
import com.yargisoft.fluenta.usecase.TextToSpeechUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommonPhraseViewModel @Inject constructor() : ViewModel() {

    private val _phrase = MutableStateFlow<CommonPhrase?>(null)
    val phrase: StateFlow<CommonPhrase?> get() = _phrase

    @Inject
    lateinit var textToSpeechUseCase: TextToSpeechUseCase
    @Inject
    lateinit var commonPhraseRepository: CommonPhraseRepository

    fun loadRandomMostCommonWord() {
        viewModelScope.launch {
            try {
                _phrase.value = commonPhraseRepository.getRandomCommonPhrase()
            } catch (e: Exception) {
                // Hata yönetimi eklenebilir
                _phrase.value = null
            }
        }
    }


    fun speak(text: String, context: Context) {
        if (textToSpeechUseCase.isTextToSpeechEnabled(context)){
            textToSpeechUseCase.speak(text)
        }else{
            textToSpeechUseCase.alertBuilderForTextToSpeech(context)
        }
    }

    fun stop() {
        textToSpeechUseCase.stop()
    }

}