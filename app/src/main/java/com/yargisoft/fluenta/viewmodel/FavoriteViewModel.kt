package com.yargisoft.fluenta.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yargisoft.fluenta.data.model.Favorite
import com.yargisoft.fluenta.data.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: FavoriteRepository
) : ViewModel() {

    private val _favorites = MutableStateFlow<List<Favorite>>(emptyList())
    val favorites: StateFlow<List<Favorite>> = _favorites

    fun loadFavorites(category: String) {
        viewModelScope.launch {
            repository.getFavoritesByCategory(category).collect { words ->
                _favorites.value = words
            }
        }
    }

    fun addFavorite(word: String, category: String) {
        viewModelScope.launch {
            repository.addFavorite(word, category)
        }
    }

    fun removeFavorite(word: String, category: String) {
        viewModelScope.launch {
            repository.removeFavorite(word, category)
        }
    }

    suspend fun isWordFavorite(word: String, category: String): Boolean {
        return repository.isWordFavorite(word, category)
    }
}
