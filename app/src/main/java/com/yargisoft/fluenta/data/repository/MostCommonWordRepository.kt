package com.yargisoft.fluenta.data.repository

import com.yargisoft.fluenta.data.model.MostCommonWord
import com.yargisoft.fluenta.data.model.MostCommonWordDao
import com.yargisoft.fluenta.data.model.OxfordWord
import com.yargisoft.fluenta.data.model.OxfordWordDao
import javax.inject.Inject

class MostCommonWordRepository @Inject constructor(
    private val mostCommonWordDao: MostCommonWordDao
) {
    suspend fun getRandomOxfordWord(): MostCommonWord {
        return mostCommonWordDao.getRandomMostCommonWord()
    }
}
