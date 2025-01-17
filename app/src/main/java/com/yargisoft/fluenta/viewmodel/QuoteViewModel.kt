package com.yargisoft.fluenta.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yargisoft.fluenta.data.model.Quote
import com.yargisoft.fluenta.data.repository.QuoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel @Inject constructor(
    private val repository: QuoteRepository
) : ViewModel() {

    private val _quote = MutableStateFlow<Quote?>(null)
    val quote: StateFlow<Quote?> get() = _quote

    fun loadRandomQuote() {
        viewModelScope.launch {
            try {
                _quote.value = repository.getRandomQuote()
            } catch (e: Exception) {
                // Hata yönetimi eklenebilir
                _quote.value = null
            }
        }
    }
}
