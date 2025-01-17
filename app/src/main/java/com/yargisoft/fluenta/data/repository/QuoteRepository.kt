package com.yargisoft.fluenta.data.repository

import com.yargisoft.fluenta.data.model.Quote
import com.yargisoft.fluenta.data.model.QuoteDao
import javax.inject.Inject

class QuoteRepository @Inject constructor(
    private val quoteDao: QuoteDao
) {
    suspend fun getRandomQuote(): Quote {
        return quoteDao.getRandomQuote()
    }
}
