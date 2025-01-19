package com.yargisoft.fluenta.data.model

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatabaseInitializer(private val context: Context, private val quoteDao: QuoteDao, private val oxfordWordDao: OxfordWordDao) {

    suspend fun quoteInitializer() = withContext(Dispatchers.IO) {
        val json = context.assets.open("quotes.json").bufferedReader().use { it.readText() }
        val quotes: List<Quote> = Gson().fromJson(json, object : TypeToken<List<Quote>>() {}.type)
        quoteDao.insertAll(quotes)
    }
    suspend fun oxfordWordInitializer() = withContext(Dispatchers.IO) {
        val json = context.assets.open("oxford_words.json").bufferedReader().use { it.readText() }
        val word: List<OxfordWord> = Gson().fromJson(json, object : TypeToken<List<OxfordWord>>() {}.type)
        oxfordWordDao.insertAll(word)
    }
}
