package com.yargisoft.fluenta.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yargisoft.fluenta.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var authRepository : AuthRepository

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _registerResult = MutableLiveData<Result<String>>()
    val registerResult: LiveData<Result<String>> get() = _registerResult


    private val _loginResult = MutableLiveData<Result<String>>()
    val loginResult: LiveData<Result<String>> get() = _loginResult

    fun register(fullName: String, email: String, password: String) {
        if (fullName.isBlank()) {
            _registerResult.value = Result.failure(Exception("Ad ve soyad alanı boş olamaz!"))
            return
        }
        if (!isValidEmail(email)) {
            _registerResult.value = Result.failure(Exception("Geçersiz e-posta!"))
            return
        }
        if (password.length < 6) {
            _registerResult.value = Result.failure(Exception("Şifre en az 6 karakter olmalı!"))
            return
        }

        _loading.value = true
        viewModelScope.launch {
            val result = authRepository.registerUser(email, password, fullName)
            _registerResult.value = result
            _loading.value = false
        }
    }



    fun login(email: String, password: String) {
        if (!isValidEmail(email)) {
            _loginResult.value = Result.failure(Exception("Geçersiz e-posta!"))
            return
        }
        if (password.isBlank()) {
            _loginResult.value = Result.failure(Exception("Şifre alanı boş olamaz!"))
            return
        }

        _loading.value = true
        viewModelScope.launch {
            val result = authRepository.loginUser(email, password)
            _loginResult.value = result
            _loading.value = false
        }
    }


    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}