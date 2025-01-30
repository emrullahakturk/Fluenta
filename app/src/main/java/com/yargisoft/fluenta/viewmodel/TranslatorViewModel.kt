package com.yargisoft.fluenta.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yargisoft.fluenta.data.repository.TranslatorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TranslatorViewModel @Inject constructor(
    private val repository: TranslatorRepository
) : ViewModel() {

    private val _translatedText = MutableStateFlow("")
    val translatedText: StateFlow<String> = _translatedText

    fun translate(text: String, sourceLang: String, targetLang: String) {
        viewModelScope.launch {
            val result = repository.translateText(text, sourceLang, targetLang)
            _translatedText.value = result
        }
    }
}
