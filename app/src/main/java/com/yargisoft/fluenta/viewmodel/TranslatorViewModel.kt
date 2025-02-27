package com.yargisoft.fluenta.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yargisoft.fluenta.data.repository.TranslatorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TranslatorViewModel @Inject constructor(
    private val repository: TranslatorRepository
) : ViewModel() {

    private val _translatedText = MutableStateFlow("")
    val translatedText = _translatedText.asStateFlow()

    private var currentSourceLang = "en"
    private var currentTargetLang = "tr"

    fun translate(text: String) {
        if (text.isNotBlank()) {
            viewModelScope.launch {
                val translation = repository.translateText(text, currentSourceLang, currentTargetLang)
                _translatedText.value = translation
            }
        }
    }

    fun swapLanguages(text: String) {
        val tempLang = currentSourceLang
        currentSourceLang = currentTargetLang
        currentTargetLang = tempLang

        // Kaynak ve hedef dil isimlerini güncellemek için livedata kullanılabilir.
        if (currentSourceLang == "en") {
            _translatedText.value = "English"
        } else {
            _translatedText.value = "Türkçe"
        }

        // Swap sonrası çeviriyi tekrar yap
        translate(text)
    }
}
