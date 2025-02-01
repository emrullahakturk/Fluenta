package com.yargisoft.fluenta.di.module
import android.content.Context
import android.speech.tts.TextToSpeech
import android.widget.Toast
import com.yargisoft.fluenta.usecase.TextToSpeechUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TextToSpeechModule {

    @Provides
    @Singleton
    fun provideTextToSpeech(@ApplicationContext context: Context): TextToSpeech {
        return TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val tts = TextToSpeech(context, null)
                //Toast.makeText(context, "TTS bAŞARLI", Toast.LENGTH_SHORT).show()
                tts.setLanguage(Locale.US)// ✅ Dil ayarı ingilizce yapıldı
            }
        }
    }

    @Provides
    @Singleton
    fun provideSpeakTextUseCase(textToSpeech: TextToSpeech): TextToSpeechUseCase {
        return TextToSpeechUseCase(textToSpeech)
    }
}
