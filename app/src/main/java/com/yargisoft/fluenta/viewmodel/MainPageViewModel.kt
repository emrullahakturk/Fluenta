package com.yargisoft.fluenta.viewmodel

import androidx.lifecycle.ViewModel
import com.yargisoft.fluenta.data.model.MainPageMenuItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor(
    private val items: List<MainPageMenuItem>
) : ViewModel() {

    private val _mainPageItems = MutableStateFlow(items)
    val mainPageItems: StateFlow<List<MainPageMenuItem>> = _mainPageItems

}
