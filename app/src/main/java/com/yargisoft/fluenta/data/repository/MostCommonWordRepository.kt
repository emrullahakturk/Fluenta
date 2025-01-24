package com.yargisoft.fluenta.data.repository

import com.yargisoft.fluenta.data.model.MostCommonWord
import com.yargisoft.fluenta.data.model.MostCommonWordDao
import javax.inject.Inject

class MostCommonWordRepository @Inject constructor(
    private val mostCommonWordDao: MostCommonWordDao
) {
    suspend fun getRandomMostCommonWord(): MostCommonWord {
        return mostCommonWordDao.getRandomMostCommonWord()
    }
}
