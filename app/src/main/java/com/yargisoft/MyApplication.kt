package com.yargisoft

import android.app.Application
import com.yargisoft.fluenta.data.model.DatabaseInitializer
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application() {

    @Inject
    lateinit var databaseInitializer: DatabaseInitializer

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        // Veritabanını doldurmak için coroutine başlat
        applicationScope.launch {
            databaseInitializer.apply {
                quoteInitializer()
                oxfordWordInitializer()
                mostCommonWordInitializer()
                commonPhraseInitializer()
                c1C2Initializer()
            }
        }
    }
}