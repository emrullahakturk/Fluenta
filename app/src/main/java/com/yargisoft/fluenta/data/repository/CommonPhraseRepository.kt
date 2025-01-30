package com.yargisoft.fluenta.data.repository

import com.yargisoft.fluenta.data.model.CommonPhrase
import com.yargisoft.fluenta.data.model.CommonPhraseDao
import javax.inject.Inject

class CommonPhraseRepository @Inject constructor(
    private val commonPhraseDao: CommonPhraseDao
) {
    suspend fun getRandomCommonPhrase(): CommonPhrase {
        return commonPhraseDao.getRandomCommonPhrase()
    }
}