package com.yargisoft.fluenta.data.model

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatabaseInitializer(private val context: Context, private val quoteDao: QuoteDao) {

    suspend fun populateDatabase() = withContext(Dispatchers.IO) {
        val json = context.assets.open("quotes.json").bufferedReader().use { it.readText() }
        val quotes: List<Quote> = Gson().fromJson(json, object : TypeToken<List<Quote>>() {}.type)
        quoteDao.insertAll(quotes)
    }
}
