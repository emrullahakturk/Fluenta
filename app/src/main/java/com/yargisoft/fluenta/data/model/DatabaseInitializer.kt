package com.yargisoft.fluenta.data.model

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class DatabaseInitializer @Inject constructor(
    private val context: Context,
    private val quoteDao: QuoteDao,
    private val oxfordWordDao: OxfordWordDao,
    private val mostCommonWordDao: MostCommonWordDao,
    private val commonPhraseDao: CommonPhraseDao,
    private val storyDao: StoryDao,
    ) {

    suspend fun quoteInitializer() = withContext(Dispatchers.IO) {
        val json = context.assets.open("quotes.json").bufferedReader().use { it.readText() }
        val quotes: List<Quote> = Gson().fromJson(json, object : TypeToken<List<Quote>>() {}.type)
        quoteDao.insertAll(quotes)
    }

    suspend fun oxfordWordInitializer() = withContext(Dispatchers.IO) {
        val json = context.assets.open("oxford_words.json").bufferedReader().use { it.readText() }
        val word: List<OxfordWord> =
            Gson().fromJson(json, object : TypeToken<List<OxfordWord>>() {}.type)
        oxfordWordDao.insertAll(word)
    }

    suspend fun mostCommonWordInitializer() = withContext(Dispatchers.IO) {
        val json =
            context.assets.open("most_common_c1_c2.json").bufferedReader().use { it.readText() }
        val word: List<MostCommonWord> =
            Gson().fromJson(json, object : TypeToken<List<MostCommonWord>>() {}.type)
        mostCommonWordDao.insertAll(word)
    }

    suspend fun commonPhraseInitializer() = withContext(Dispatchers.IO) {
        val json = context.assets.open("common_phrases.json").bufferedReader().use { it.readText() }
        val phrase: List<CommonPhrase> =
            Gson().fromJson(json, object : TypeToken<List<CommonPhrase>>() {}.type)
        commonPhraseDao.insertAll(phrase)
    }

    suspend fun c1C2Initializer() = withContext(Dispatchers.IO) {
        val json = context.assets.open("stories.json").bufferedReader().use { it.readText() }
        val story: List<Story> =
            Gson().fromJson(json, object : TypeToken<List<Story>>() {}.type)
        storyDao.insertAll(story)
    }
}
