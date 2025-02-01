package com.yargisoft.fluenta.usecase

import android.content.Context
import android.content.Intent
import android.speech.tts.TextToSpeech
import androidx.appcompat.app.AlertDialog
import java.util.Locale
import javax.inject.Inject

class TextToSpeechUseCase @Inject constructor(
    private val textToSpeech: TextToSpeech,
) {
    fun speak(text: String) {
        textToSpeech.setLanguage(Locale.US)
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    fun stop() {
        textToSpeech.stop()
    }

    fun isTextToSpeechEnabled(context: Context): Boolean {
        val tts = TextToSpeech(context, null)
        val engines: List<TextToSpeech.EngineInfo> = tts.engines
        return engines.isNotEmpty() // Eğer boş değilse, TTS motoru mevcut demektir
    }

    private fun openTextToSpeechSettings(context: Context) {
        val intent = Intent().apply {
            action = "com.android.settings.TTS_SETTINGS"
        }
        context.startActivity(intent)
    }

    fun alertBuilderForTextToSpeech(context: Context) {
        AlertDialog.Builder(context)
            .setTitle("Text-to-Speech Devre Dışı")
            .setMessage("Google TTS motorunuz devre dışı. Seslendirme özelliğini kullanabilmek için etkinleştirmeniz gerekiyor.")
            .setPositiveButton("Ayarları Aç") { _, _ ->
                openTextToSpeechSettings(context)
            }
            .setNegativeButton("İptal", null)
            .show()

    }
}

