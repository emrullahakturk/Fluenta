package com.yargisoft.fluenta.data.repository

import com.yargisoft.fluenta.data.network.TranslateApiService
import javax.inject.Inject

class TranslatorRepository @Inject constructor(
    private val apiService: TranslateApiService
) {
    suspend fun translateText(text: String, sourceLang: String, targetLang: String): String {
        return try {
            val response = apiService.translate(sourceLang, targetLang, text)
            val translatedText = (response[0] as List<*>)[0] as List<*>
            translatedText[0].toString()
        } catch (e: Exception) {
            "Çeviri yapılamadı: ${e.message}"
        }
    }
}
