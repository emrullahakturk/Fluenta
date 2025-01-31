package com.yargisoft.fluenta.data.repository

import com.yargisoft.fluenta.data.network.TranslateApiService
import javax.inject.Inject


class TranslatorRepository @Inject constructor(
    private val apiService: TranslateApiService
) {
    suspend fun translateText(text: String, sourceLang: String, targetLang: String): String {
        return try {
            val response = apiService.translate(sourceLang, targetLang, text)

            // Dönen cevabı düzeltme: Tüm çevirileri birleştiriyoruz.
            val translatedText = StringBuilder()
            val translatedSegments = response[0] as List<*>

            for (segment in translatedSegments) {
                val segmentList = segment as List<*>
                translatedText.append(segmentList[0].toString()).append(" ") // Cümleleri birleştir
            }

            translatedText.toString().trim() // Fazla boşlukları temizle
        } catch (e: Exception) {
            "Çeviri yapılamadı: ${e.message}"
        }
    }
}
